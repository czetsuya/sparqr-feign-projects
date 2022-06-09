package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.persistence.entities.PlatformUserCertificationEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserCertificationRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.exceptions.UserNotFoundException;
import com.sparqr.cx.iam.services.pojos.PlatformUserCertification;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlatformUserCertificationServiceImpl implements PlatformUserCertificationService {

  private final PlatformUserRepository platformUserRepository;
  private final PlatformUserCertificationRepository platformUserCertificationRepository;
  private final Service2PersistenceMapper service2PersistenceMapper;
  private final Persistence2ModelMapper persistence2ModelMapper;

  @Transactional
  @Override
  public CompletableFuture<PlatformUserCertification> createOrUpdate(final String userAlias, final Long id,
      final PlatformUserCertification platformUserCertification) {

    Assert.hasLength(userAlias, "userAlias cannot be null");
    Assert.notNull(platformUserCertification, "platformUserCertification cannot be null");

    log.info("upsert certification for user with alias={}, id={}, and certification={}", userAlias,
        id, platformUserCertification);

    return CompletableFuture.supplyAsync(() -> {

      AtomicReference<PlatformUserCertification> atomicPlatformUserCertification = new AtomicReference<>();

      platformUserRepository.findByAlias(userAlias)
          .ifPresentOrElse(platformUserEntity -> {
                Optional.ofNullable(id).ifPresentOrElse(
                    platformUserCertificationId -> {
                      // update
                      platformUserCertificationRepository.findById(platformUserCertificationId)
                          .ifPresent(
                              platformUserCertificationEntity -> {
                                service2PersistenceMapper.toPlatformUserCertificationEntity(platformUserCertification,
                                    platformUserCertificationEntity);
                                platformUserCertificationRepository.save(platformUserCertificationEntity);
                                atomicPlatformUserCertification.set(
                                    persistence2ModelMapper.toPlatformUserCertification(platformUserCertificationEntity));
                              });
                    },
                    () -> {
                      // save
                      PlatformUserCertificationEntity newPlatformUserCertificationEntity =
                          service2PersistenceMapper.toPlatformUserCertificationEntity(platformUserCertification);
                      newPlatformUserCertificationEntity.setPlatformUser(platformUserEntity);
                      atomicPlatformUserCertification.set(
                          persistence2ModelMapper.toPlatformUserCertification(
                              platformUserCertificationRepository.save(newPlatformUserCertificationEntity)));
                    });
              },
              () -> {
                throw new UserNotFoundException(userAlias);
              });

      return atomicPlatformUserCertification.get();
    });
  }

  @Override
  public CompletableFuture<Void> delete(Long id) {

    log.info("removing certification with id={}", id);

    return CompletableFuture.runAsync(() -> {
      platformUserCertificationRepository.deleteById(id);
    });
  }

  @Override
  public CompletableFuture<List<PlatformUserCertification>> list(String userAlias) {

    log.info("requesting all the languages");

    return CompletableFuture.supplyAsync(() -> persistence2ModelMapper.toPlatformUserCertification(
        platformUserCertificationRepository.findByPlatformUserAlias(userAlias)));
  }
}
