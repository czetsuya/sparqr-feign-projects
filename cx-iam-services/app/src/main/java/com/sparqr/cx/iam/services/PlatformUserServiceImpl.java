package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformSsoRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.aws.AwsCognitoClient;
import com.sparqr.cx.iam.services.exceptions.UserNotFoundException;
import com.sparqr.cx.iam.services.pojos.PlatformUser;
import com.sparqr.cx.iam.services.pojos.PlatformUserProfile;
import com.sparqr.cx.iam.services.pojos.UserDetail;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlatformUserServiceImpl implements PlatformUserService {

  private final PlatformUserRepository platformUserRepository;
  private final PlatformUserProfileService platformUserProfileService;
  private final PlatformSsoRepository platformSsoRepository;
  private final Service2PersistenceMapper service2PersistenceMapper;
  private final Persistence2ModelMapper persistence2ModelMapper;
  private final AwsCognitoClient awsCognitoClient;
  private final Executor executor;

  @Override
  @Transactional
  public CompletableFuture<UserDetail> update(String userAlias, UserDetail userDetail) {

    return update(userAlias, userDetail.getUser())
        .thenCompose(
            platformUser -> createOrUpdateThenReturn(platformUser, userDetail.getProfile()));
  }

  @Override
  public CompletableFuture<PlatformUser> update(String userAlias, PlatformUser platformUser) {

    Assert.notNull("alias", "alias cannot be null");

    return CompletableFuture.supplyAsync(() -> {
      Optional<PlatformUserEntity> optPlatformUserEntity = platformUserRepository.findByAlias(userAlias);
      if (optPlatformUserEntity.isPresent()) {
        PlatformUserEntity entity = optPlatformUserEntity.get();
        service2PersistenceMapper.toPlatformUserEntity(platformUser, entity);
        return persistence2ModelMapper.toPlatformUser(platformUserRepository.save(entity));

      } else {
        throw new UserNotFoundException(userAlias);
      }
    });
  }

  @Override
  public CompletableFuture<PlatformUser> getUser(String userAlias) {

    Assert.hasLength(userAlias, "userAlias cannot be null");

    return CompletableFuture.supplyAsync(() -> {

      AtomicReference<PlatformUser> atomicPlatformUserDto = new AtomicReference<>();

      platformUserRepository.findByAlias(userAlias)
          .ifPresentOrElse(
              platformUserEntity -> {
                atomicPlatformUserDto.set(persistence2ModelMapper.toPlatformUser(platformUserEntity));
              },
              () -> {
                throw new UserNotFoundException(userAlias);
              }
          );

      return atomicPlatformUserDto.get();
    });
  }

  @Override
  public CompletableFuture<Void> updateAlias(String oldAlias, String newAlias) {

    Assert.hasLength(oldAlias, "oldAlias cannot be null");
    Assert.hasLength(newAlias, "newAlias cannot be null");

    log.info("updating user with oldAlias={} to newAlias={}", oldAlias, newAlias);

    return CompletableFuture.runAsync(() -> {

      platformUserRepository.findByAlias(oldAlias)
          .ifPresentOrElse(
              platformUserEntity -> {
                platformUserEntity.setAlias(newAlias);
                platformUserRepository.save(platformUserEntity);

                updateExternalUsers(platformUserEntity);
              },
              () -> {
                throw new UserNotFoundException(oldAlias);
              }
          );
    }, executor);
  }

  private void updateExternalUsers(PlatformUserEntity platformUserEntity) {

    Optional.ofNullable(platformSsoRepository.findByPlatformUserId(platformUserEntity.getId()))
        .ifPresent(e -> e.stream()
            .forEach(f -> updateExternalUser(f.getExternalRef(), platformUserEntity.getAlias())));
  }

  private void updateExternalUser(String externalRef, String alias) {
    awsCognitoClient.updateUserAttributeByAlias(externalRef, alias);
  }

  private CompletableFuture<UserDetail> createOrUpdateThenReturn(PlatformUser platformUser,
      PlatformUserProfile platformUserProfile) {

    return platformUserProfileService.updateOrCreateIfAbsent(platformUser.getAlias(), platformUserProfile)
        .thenCombine(
            CompletableFuture.completedFuture(platformUser),
            (e1, e2) -> new UserDetail(e2, persistence2ModelMapper.toPlatformUserProfile(e1)));
  }
}
