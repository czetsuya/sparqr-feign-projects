package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserProfileEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserProfileRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.exceptions.UserNotFoundException;
import com.sparqr.cx.iam.services.pojos.PlatformUserProfile;
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
public class PlatformUserProfileServiceImpl implements PlatformUserProfileService {

  private final PlatformUserRepository platformUserRepository;
  private final PlatformUserProfileRepository platformUserProfileRepository;
  private final Service2PersistenceMapper service2PersistenceMapper;
  private final Persistence2ModelMapper persistence2ModelMapper;

  @Override
  public CompletableFuture<PlatformUserProfileEntity> updateOrCreateIfAbsent(String alias,
      PlatformUserProfile platformUserProfile) {

    Assert.notNull(alias, "alias is required");

    return CompletableFuture.supplyAsync(() -> {

      Optional<PlatformUserEntity> optPlatformUserEntity = platformUserRepository.findByAlias(alias);
      if (optPlatformUserEntity.isEmpty()) {
        throw new UserNotFoundException(alias);
      }

      AtomicReference<PlatformUserProfileEntity> atomicPlatformUserProfileEntity = new AtomicReference<>(
          new PlatformUserProfileEntity());

      platformUserProfileRepository.findByPlatformUserAlias(alias)
          .ifPresent(atomicPlatformUserProfileEntity::set);

      PlatformUserProfileEntity platformUserProfileEntity = new PlatformUserProfileEntity();
      if (Optional.ofNullable(atomicPlatformUserProfileEntity.get()).isPresent()) {
        platformUserProfileEntity = atomicPlatformUserProfileEntity.get();
      }

      service2PersistenceMapper.toPlatformUserProfileEntity(platformUserProfile, platformUserProfileEntity);
      platformUserProfileEntity.setPlatformUser(optPlatformUserEntity.get());
      platformUserProfileRepository.save(platformUserProfileEntity);

      return platformUserProfileEntity;
    });
  }

  @Override
  public CompletableFuture<PlatformUserProfile> getProfile(String userAlias) {

    Assert.hasLength(userAlias, "userAlias cannot be null");

    return CompletableFuture.supplyAsync(() -> {

      AtomicReference<PlatformUserProfile> atomicPlatformUserProfileDto = new AtomicReference<>();

      platformUserProfileRepository.findByPlatformUserAlias(userAlias)
          .ifPresent(
              platformUserProfileEntity -> {
                atomicPlatformUserProfileDto.set(
                    persistence2ModelMapper.toPlatformUserProfile(platformUserProfileEntity));
              }
          );

      return atomicPlatformUserProfileDto.get();
    });
  }
}
