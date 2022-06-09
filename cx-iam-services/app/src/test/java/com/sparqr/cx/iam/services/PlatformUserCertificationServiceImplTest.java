package com.sparqr.cx.iam.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.persistence.entities.PlatformUserCertificationEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserCertificationRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.exceptions.UserNotFoundException;
import com.sparqr.cx.iam.services.pojos.PlatformUserCertification;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class PlatformUserCertificationServiceImplTest {

  private PlatformUserCertificationServiceImpl platformUserCertificationService;

  @Mock
  private Service2PersistenceMapper service2PersistenceMapper;

  @Mock
  private PlatformUserRepository platformUserRepository;

  @Mock
  private PlatformUserCertificationRepository platformUserCertificationRepository;

  @Mock
  private Persistence2ModelMapper persistence2ModelMapper;

  @BeforeEach
  private void setup() {
    platformUserCertificationService = new PlatformUserCertificationServiceImpl(platformUserRepository,
        platformUserCertificationRepository, service2PersistenceMapper, persistence2ModelMapper);
  }

  @Test
  public void ShouldThrowUserNotFoundException() {

    String alias = "czetsuya";
    Mockito.when(platformUserRepository.findByAlias(alias)).thenReturn(Optional.empty());

    Assertions.assertThatExceptionOfType(ExecutionException.class).isThrownBy(() -> {
      platformUserCertificationService.createOrUpdate(alias, null, PlatformUserCertification.builder().build()).get();
    }).withCause(new UserNotFoundException(alias));
  }

  @Test
  public void ShouldThrowAliasCannotBeNull() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
      platformUserCertificationService.createOrUpdate(null, null, PlatformUserCertification.builder().build()).get();
    }).withMessage("userAlias cannot be null");
  }

  @Test
  public void ShouldThrowCertificationCannotBeNull() {

    String alias = "czetsuya";

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
      platformUserCertificationService.createOrUpdate(alias, null, null).get();
    }).withMessage("platformUserCertification cannot be null");
  }

  @Test
  @SneakyThrows
  public void ShouldCreateIfIdIsNull() {

    String alias = "czetsuya";

    PlatformUserEntity platformUserEntity = PlatformUserEntity.builder().build();
    PlatformUserCertification platformUserCertification = PlatformUserCertification.builder().build();
    PlatformUserCertificationEntity platformUserCertificationEntity = PlatformUserCertificationEntity.builder().build();
    platformUserCertificationEntity.setPlatformUser(platformUserEntity);

    when(platformUserRepository.findByAlias(alias))
        .thenReturn(Optional.of(platformUserEntity));
    when(service2PersistenceMapper.toPlatformUserCertificationEntity(platformUserCertification))
        .thenReturn(platformUserCertificationEntity);
    when(platformUserCertificationRepository.save(platformUserCertificationEntity))
        .thenReturn(platformUserCertificationEntity);
    when(persistence2ModelMapper.toPlatformUserCertification(platformUserCertificationEntity)).thenReturn(
        platformUserCertification);

    platformUserCertificationService.createOrUpdate(alias, null, platformUserCertification).get();

    verify(platformUserRepository, times(1)).findByAlias(alias);
    verify(service2PersistenceMapper, times(1)).toPlatformUserCertificationEntity(any());
    verify(platformUserCertificationRepository, times(1)).save(any());
    verify(persistence2ModelMapper, times(1)).toPlatformUserCertification(platformUserCertificationEntity);
  }

  @Test
  @SneakyThrows
  public void ShouldUpdateIfIdIsNotNull() {

    String alias = "czetsuya";
    Long entityId = 1000L;

    PlatformUserEntity platformUserEntity = PlatformUserEntity.builder().build();
    PlatformUserCertification platformUserCertification = PlatformUserCertification.builder().build();
    PlatformUserCertificationEntity platformUserCertificationEntity = PlatformUserCertificationEntity.builder().build();
    platformUserCertificationEntity.setPlatformUser(platformUserEntity);

    when(platformUserRepository.findByAlias(alias))
        .thenReturn(Optional.of(platformUserEntity));
    when(platformUserCertificationRepository.findById(entityId))
        .thenReturn(Optional.of(platformUserCertificationEntity));
    when(platformUserCertificationRepository.save(platformUserCertificationEntity))
        .thenReturn(platformUserCertificationEntity);
    when(persistence2ModelMapper.toPlatformUserCertification(platformUserCertificationEntity)).thenReturn(
        platformUserCertification);

    platformUserCertificationService.createOrUpdate(alias, entityId, platformUserCertification).get();

    verify(platformUserRepository, times(1)).findByAlias(alias);
    verify(service2PersistenceMapper, times(1)).toPlatformUserCertificationEntity(any(), any());
    verify(platformUserCertificationRepository, times(1)).save(any());
    verify(persistence2ModelMapper, times(1)).toPlatformUserCertification(platformUserCertificationEntity);
  }
}
