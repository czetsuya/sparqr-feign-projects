package com.sparqr.cx.iam.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEducationEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserEducationRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.exceptions.UserNotFoundException;
import com.sparqr.cx.iam.services.pojos.PlatformUserEducation;
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
public class PlatformUserEducationServiceImplTest {

  private PlatformUserEducationServiceImpl platformUserEducationService;

  @Mock
  private Service2PersistenceMapper service2PersistenceMapper;

  @Mock
  private PlatformUserRepository platformUserRepository;

  @Mock
  private PlatformUserEducationRepository platformUserEducationRepository;

  @Mock
  private Persistence2ModelMapper persistence2ModelMapper;

  @BeforeEach
  private void setup() {
    platformUserEducationService = new PlatformUserEducationServiceImpl(platformUserRepository,
        platformUserEducationRepository, service2PersistenceMapper, persistence2ModelMapper);
  }

  @Test
  public void ShouldThrowUserNotFoundException() {

    String alias = "czetsuya";
    Mockito.when(platformUserRepository.findByAlias(alias)).thenReturn(Optional.empty());

    Assertions.assertThatExceptionOfType(ExecutionException.class).isThrownBy(() -> {
      platformUserEducationService.createOrUpdate(alias, null, PlatformUserEducation.builder().build()).get();
    }).withCause(new UserNotFoundException(alias));
  }

  @Test
  public void ShouldThrowAliasCannotBeNull() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
      platformUserEducationService.createOrUpdate(null, null, PlatformUserEducation.builder().build()).get();
    }).withMessage("userAlias cannot be null");
  }

  @Test
  public void ShouldThrowEducationCannotBeNull() {

    String alias = "czetsuya";

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
      platformUserEducationService.createOrUpdate(alias, null, null).get();
    }).withMessage("platformUserEducation cannot be null");
  }

  @Test
  @SneakyThrows
  public void ShouldCreateIfIdIsNull() {

    String alias = "czetsuya";

    PlatformUserEntity platformUserEntity = PlatformUserEntity.builder().build();
    PlatformUserEducation platformUserEducation = PlatformUserEducation.builder().build();
    PlatformUserEducationEntity platformUserEducationEntity = PlatformUserEducationEntity.builder().build();
    platformUserEducationEntity.setPlatformUser(platformUserEntity);

    when(platformUserRepository.findByAlias(alias))
        .thenReturn(Optional.of(platformUserEntity));
    when(service2PersistenceMapper.toPlatformUserEducationEntity(platformUserEducation))
        .thenReturn(platformUserEducationEntity);
    when(platformUserEducationRepository.save(platformUserEducationEntity))
        .thenReturn(platformUserEducationEntity);
    when(persistence2ModelMapper.toPlatformUserEducation(platformUserEducationEntity)).thenReturn(
        platformUserEducation);

    platformUserEducationService.createOrUpdate(alias, null, platformUserEducation).get();

    verify(platformUserRepository, times(1)).findByAlias(alias);
    verify(service2PersistenceMapper, times(1)).toPlatformUserEducationEntity(any());
    verify(platformUserEducationRepository, times(1)).save(any());
    verify(persistence2ModelMapper, times(1)).toPlatformUserEducation(platformUserEducationEntity);
  }

  @Test
  @SneakyThrows
  public void ShouldUpdateIfIdIsNotNull() {

    String alias = "czetsuya";
    Long entityId = 1000L;

    PlatformUserEntity platformUserEntity = PlatformUserEntity.builder().build();
    PlatformUserEducation platformUserEducation = PlatformUserEducation.builder().build();
    PlatformUserEducationEntity platformUserEducationEntity = PlatformUserEducationEntity.builder().build();
    platformUserEducationEntity.setPlatformUser(platformUserEntity);

    when(platformUserRepository.findByAlias(alias))
        .thenReturn(Optional.of(platformUserEntity));
    when(platformUserEducationRepository.findById(entityId))
        .thenReturn(Optional.of(platformUserEducationEntity));
    when(platformUserEducationRepository.save(platformUserEducationEntity))
        .thenReturn(platformUserEducationEntity);
    when(persistence2ModelMapper.toPlatformUserEducation(platformUserEducationEntity)).thenReturn(
        platformUserEducation);

    platformUserEducationService.createOrUpdate(alias, entityId, platformUserEducation).get();

    verify(platformUserRepository, times(1)).findByAlias(alias);
    verify(service2PersistenceMapper, times(1)).toPlatformUserEducationEntity(any(), any());
    verify(platformUserEducationRepository, times(1)).save(any());
    verify(persistence2ModelMapper, times(1)).toPlatformUserEducation(platformUserEducationEntity);
  }
}
