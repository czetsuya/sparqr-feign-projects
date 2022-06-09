package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.exceptions.UserNotFoundException;
import java.util.Set;
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
public class PlatformUserSkillServiceImpl implements PlatformUserSkillService {

  private final PlatformUserRepository platformUserRepository;

  @Override
  @Transactional
  public CompletableFuture<Set<String>> createOrUpdate(String userAlias, Set<String> skills) {

    Assert.hasLength(userAlias, "userAlias cannot be null");
    Assert.notNull(skills, "skills cannot be null");

    log.info("upsert skills for user with alias={} and skills={}", userAlias, skills);

    return CompletableFuture.supplyAsync(() -> {

      AtomicReference<Set<String>> atomicSkills = new AtomicReference<>();

      platformUserRepository.findByAlias(userAlias)
          .ifPresentOrElse(platformUserEntity -> {
                platformUserEntity.setSkills(skills);
                platformUserRepository.save(platformUserEntity);
                atomicSkills.set(platformUserEntity.getSkills());
              },
              () -> {
                throw new UserNotFoundException(userAlias);
              });

      return atomicSkills.get();
    });
  }

  @Override
  public CompletableFuture<Set<String>> getSkills(String userAlias) {

    Assert.hasLength(userAlias, "userAlias cannot be null");

    log.info("retrieving skills for user with alias={}", userAlias);

    return CompletableFuture.supplyAsync(() -> {

      AtomicReference<Set<String>> atomicSkills = new AtomicReference<>();

      platformUserRepository.findByAlias(userAlias)
          .ifPresentOrElse(platformUserEntity -> {
                atomicSkills.set(platformUserEntity.getSkills());
              },
              () -> {
                throw new UserNotFoundException(userAlias);
              });

      return atomicSkills.get();
    });
  }
}