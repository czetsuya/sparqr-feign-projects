package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.persistence.entities.PlatformUserLanguageEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserLanguageRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.exceptions.UserNotFoundException;
import com.sparqr.cx.iam.services.pojos.PlatformUserLanguage;
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
public class PlatformUserLanguageServiceImpl implements PlatformUserLanguageService {

  private final PlatformUserRepository platformUserRepository;
  private final PlatformUserLanguageRepository platformUserLanguageRepository;
  private final Service2PersistenceMapper service2PersistenceMapper;
  private final Persistence2ModelMapper persistence2ModelMapper;

  @Override
  @Transactional
  public CompletableFuture<PlatformUserLanguage> createOrUpdate(String userAlias, Long id,
      PlatformUserLanguage platformUserLanguage) {

    Assert.hasLength(userAlias, "userAlias cannot be null");
    Assert.notNull(platformUserLanguage, "platformUserLanguage cannot be null");

    log.info("upsert language for user with alias={}, id={}, and language={}", userAlias, id, platformUserLanguage);

    return CompletableFuture.supplyAsync(() -> {

      AtomicReference<PlatformUserLanguage> atomicPlatformUserLanguage = new AtomicReference<>();

      platformUserRepository.findByAlias(userAlias)
          .ifPresentOrElse(platformUserEntity -> {
                Optional.ofNullable(id).ifPresentOrElse(
                    platformUserLanguageId -> {
                      // update
                      platformUserLanguageRepository.findById(platformUserLanguageId)
                          .ifPresent(
                              platformUserLanguageEntity -> {
                                service2PersistenceMapper.toPlatformUserLanguageEntity(platformUserLanguage,
                                    platformUserLanguageEntity);
                                platformUserLanguageRepository.save(platformUserLanguageEntity);
                                atomicPlatformUserLanguage.set(
                                    persistence2ModelMapper.toPlatformUserLanguage(platformUserLanguageEntity));
                              });
                    },
                    () -> {
                      // save
                      PlatformUserLanguageEntity newPlatformUserLanguageEntity =
                          service2PersistenceMapper.toPlatformUserLanguageEntity(platformUserLanguage);
                      newPlatformUserLanguageEntity.setPlatformUser(platformUserEntity);
                      atomicPlatformUserLanguage.set(
                          persistence2ModelMapper.toPlatformUserLanguage(
                              platformUserLanguageRepository.save(newPlatformUserLanguageEntity)));
                    });
              },
              () -> {
                throw new UserNotFoundException(userAlias);
              });

      return atomicPlatformUserLanguage.get();
    });
  }

  @Override
  public CompletableFuture<Void> delete(Long id) {

    log.info("removing language with id={}", id);

    return CompletableFuture.runAsync(() -> {
      platformUserLanguageRepository.deleteById(id);
    });
  }

  @Override
  public CompletableFuture<List<PlatformUserLanguage>> list(String userAlias) {

    log.info("requesting all the languages");

    return CompletableFuture.supplyAsync(() -> persistence2ModelMapper.toPlatformUserLanguage(
        platformUserLanguageRepository.findByPlatformUserAlias(userAlias)));
  }
}