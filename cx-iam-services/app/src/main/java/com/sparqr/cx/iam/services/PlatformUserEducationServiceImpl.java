package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEducationEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserEducationRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.exceptions.UserNotFoundException;
import com.sparqr.cx.iam.services.pojos.PlatformUserEducation;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlatformUserEducationServiceImpl implements PlatformUserEducationService {

  private final PlatformUserRepository platformUserRepository;
  private final PlatformUserEducationRepository platformUserEducationRepository;
  private final Service2PersistenceMapper service2PersistenceMapper;
  private final Persistence2ModelMapper persistence2ModelMapper;

  @Override
  public CompletableFuture<PlatformUserEducation> createOrUpdate(String userAlias, Long id,
      PlatformUserEducation platformUserEducation) {

    Assert.hasLength(userAlias, "userAlias cannot be null");
    Assert.notNull(platformUserEducation, "platformUserEducation cannot be null");

    log.info("upsert education for user with alias={}, id={}, and education={}", userAlias, id, platformUserEducation);

    return CompletableFuture.supplyAsync(() -> {

      AtomicReference<PlatformUserEducation> atomicPlatformUserEducation = new AtomicReference<>();

      platformUserRepository.findByAlias(userAlias)
          .ifPresentOrElse(platformUserEntity -> {
                Optional.ofNullable(id).ifPresentOrElse(
                    platformUserEducationId -> {
                      // update
                      platformUserEducationRepository.findById(platformUserEducationId)
                          .ifPresent(
                              platformUserEducationEntity -> {
                                service2PersistenceMapper.toPlatformUserEducationEntity(platformUserEducation,
                                    platformUserEducationEntity);
                                platformUserEducationRepository.save(platformUserEducationEntity);
                                atomicPlatformUserEducation.set(
                                    persistence2ModelMapper.toPlatformUserEducation(platformUserEducationEntity));
                              });
                    },
                    () -> {
                      // save
                      PlatformUserEducationEntity newPlatformUserEducationEntity =
                          service2PersistenceMapper.toPlatformUserEducationEntity(platformUserEducation);
                      newPlatformUserEducationEntity.setPlatformUser(platformUserEntity);
                      atomicPlatformUserEducation.set(
                          persistence2ModelMapper.toPlatformUserEducation(
                              platformUserEducationRepository.save(newPlatformUserEducationEntity)));
                    });
              },
              () -> {
                throw new UserNotFoundException(userAlias);
              });

      return atomicPlatformUserEducation.get();
    });
  }

  @Override
  public CompletableFuture<Void> delete(Long id) {

    log.info("removing education with id={}", id);

    return CompletableFuture.runAsync(() -> {
      platformUserEducationRepository.deleteById(id);
    });
  }

  @Override
  public CompletableFuture<List<PlatformUserEducation>> list(String userAlias) {

    log.info("requesting all the educations");

    return CompletableFuture.supplyAsync(() -> persistence2ModelMapper.toPlatformUserEducation(
        platformUserEducationRepository.findByPlatformUserAlias(userAlias)));
  }
}
