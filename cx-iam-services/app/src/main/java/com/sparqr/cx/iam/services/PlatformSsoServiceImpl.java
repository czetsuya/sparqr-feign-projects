package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.persistence.entities.PlatformSsoEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformSsoRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.aws.AwsCognitoClient;
import com.sparqr.cx.iam.services.pojos.PlatformUser;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlatformSsoServiceImpl implements PlatformSsoService {

  private final PlatformSsoRepository platformSsoRepository;
  private final PlatformUserRepository platformUserRepository;
  private final Persistence2ModelMapper persistence2Model;
  private final Service2PersistenceMapper service2PersistenceMapper;
  private final Executor executor;
  private final AwsCognitoClient awsCognitoClient;

  private final static String ALIAS_PREFIX = "u";

  @Override
  @Transactional
  public CompletableFuture<PlatformUser> createIfNotExists(PlatformUser platformUser) {

    log.debug("Start creating or mapping sso user={}", platformUser);

    return CompletableFuture.supplyAsync(
        () -> {
          PlatformUser platformUserOut = new PlatformUser();
          platformUserOut.setExternalRef(platformUser.getExternalRef());

          Optional<PlatformSsoEntity> optOpenIdByExternalRef = platformSsoRepository.findByExternalRef(
              platformUser.getExternalRef());
          if (optOpenIdByExternalRef.isPresent()) {
            log.debug("Get user with externalRef={}", platformUser.getExternalRef());

            PlatformSsoEntity platformOpenId = optOpenIdByExternalRef.get();
            Optional<PlatformUserEntity> optUser = Optional.ofNullable(platformOpenId.getPlatformUser());
            if (optUser.isPresent()) {
              // UC1
              log.debug("Performing UC1");
              persistence2Model.toPlatformUser(optUser.get(), platformUserOut);

            } else {
              // UC2
              log.debug("Performing UC2");

              PlatformUserEntity platformUserEntity = service2PersistenceMapper.toPlatformUserEntity(platformUser);
              platformUserEntity.setAlias(platformUser.getEmail());
              platformUserRepository.save(platformUserEntity);

              platformOpenId.setPlatformUser(platformUserEntity);
              platformSsoRepository.save(platformOpenId);

              updateAlias(platformOpenId.getExternalRef(), platformUserEntity);

              persistence2Model.toPlatformUser(platformUserEntity, platformUserOut);
            }

          } else {
            log.debug("Creating open id user with externalRef={}", platformUser.getExternalRef());

            PlatformSsoEntity platformOpenId = service2PersistenceMapper.toPlatformSsoEntity(platformUser);

            PlatformUserEntity platformUserEntity = new PlatformUserEntity();
            Optional<PlatformUserEntity> optPlatformUser = platformUserRepository.findByEmail(
                platformUser.getEmail());
            if (optPlatformUser.isPresent()) {
              log.debug("Performing UC3");
              platformUserEntity = optPlatformUser.get();

            } else {
              log.debug("Performing UC4");
              service2PersistenceMapper.toPlatformUserEntity(platformUser, platformUserEntity);
              platformUserEntity.setAlias(platformUser.getEmail());
              platformUserEntity = platformUserRepository.save(platformUserEntity);
            }

            platformOpenId.setPlatformUser(platformUserEntity);
            platformSsoRepository.save(platformOpenId);

            updateAlias(platformOpenId.getExternalRef(), platformUserEntity);
            persistence2Model.toPlatformUser(platformUserEntity, platformUserOut);
          }
          log.debug("End creating or mapping sso user with platformUser={}", platformUserOut);

          return platformUserOut;
        }, executor);
  }

  private void updateAlias(String externalRef, PlatformUserEntity platformUserEntity) {

    if (!StringUtils.hasLength(platformUserEntity.getAlias()) || platformUserEntity.getEmail()
        .equals(platformUserEntity.getAlias())) {
      platformUserEntity.setAlias(ALIAS_PREFIX + platformUserEntity.getId());
      platformUserRepository.save(platformUserEntity);

      // update AWS Cognito
      awsCognitoClient.updateUserAttributeByAlias(externalRef, platformUserEntity.getAlias());
    }
  }
}