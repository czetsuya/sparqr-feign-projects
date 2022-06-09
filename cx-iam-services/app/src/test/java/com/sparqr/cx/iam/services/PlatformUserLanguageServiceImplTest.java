package com.sparqr.cx.iam.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserLanguageEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserLanguageRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.exceptions.UserNotFoundException;
import com.sparqr.cx.iam.services.pojos.PlatformUserLanguage;
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
public class PlatformUserLanguageServiceImplTest {

  private PlatformUserLanguageServiceImpl platformUserLanguageService;

  @Mock
  private Service2PersistenceMapper service2PersistenceMapper;

  @Mock
  private PlatformUserRepository platformUserRepository;

  @Mock
  private PlatformUserLanguageRepository platformUserLanguageRepository;

  @Mock
  private Persistence2ModelMapper persistence2ModelMapper;

  @BeforeEach
  private void setup() {
    platformUserLanguageService = new PlatformUserLanguageServiceImpl(platformUserRepository,
        platformUserLanguageRepository, service2PersistenceMapper, persistence2ModelMapper);
  }

  @Test
  public void ShouldThrowUserNotFoundException() {

    String alias = "czetsuya";
    Mockito.when(platformUserRepository.findByAlias(alias)).thenReturn(Optional.empty());

    Assertions.assertThatExceptionOfType(ExecutionException.class).isThrownBy(() -> {
      platformUserLanguageService.createOrUpdate(alias, null, PlatformUserLanguage.builder().build()).get();
    }).withCause(new UserNotFoundException(alias));
  }

  @Test
  public void ShouldThrowAliasCannotBeNull() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
      platformUserLanguageService.createOrUpdate(null, null, PlatformUserLanguage.builder().build()).get();
    }).withMessage("userAlias cannot be null");
  }

  @Test
  public void ShouldThrowLanguageCannotBeNull() {

    String alias = "czetsuya";

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
      platformUserLanguageService.createOrUpdate(alias, null, null).get();
    }).withMessage("platformUserLanguage cannot be null");
  }

  @Test
  @SneakyThrows
  public void ShouldCreateIfIdIsNull() {

    String alias = "czetsuya";

    PlatformUserEntity platformUserEntity = PlatformUserEntity.builder().build();
    PlatformUserLanguage platformUserLanguage = PlatformUserLanguage.builder().build();
    PlatformUserLanguageEntity platformUserLanguageEntity = PlatformUserLanguageEntity.builder().build();
    platformUserLanguageEntity.setPlatformUser(platformUserEntity);

    when(platformUserRepository.findByAlias(alias))
        .thenReturn(Optional.of(platformUserEntity));
    when(service2PersistenceMapper.toPlatformUserLanguageEntity(platformUserLanguage))
        .thenReturn(platformUserLanguageEntity);
    when(platformUserLanguageRepository.save(platformUserLanguageEntity))
        .thenReturn(platformUserLanguageEntity);
    when(persistence2ModelMapper.toPlatformUserLanguage(platformUserLanguageEntity)).thenReturn(
        platformUserLanguage);

    platformUserLanguageService.createOrUpdate(alias, null, platformUserLanguage).get();

    verify(platformUserRepository, times(1)).findByAlias(alias);
    verify(service2PersistenceMapper, times(1)).toPlatformUserLanguageEntity(any());
    verify(platformUserLanguageRepository, times(1)).save(any());
    verify(persistence2ModelMapper, times(1)).toPlatformUserLanguage(platformUserLanguageEntity);
  }

  @Test
  @SneakyThrows
  public void ShouldUpdateIfIdIsNotNull() {

    String alias = "czetsuya";
    Long entityId = 1000L;

    PlatformUserEntity platformUserEntity = PlatformUserEntity.builder().build();
    PlatformUserLanguage platformUserLanguage = PlatformUserLanguage.builder().build();
    PlatformUserLanguageEntity platformUserLanguageEntity = PlatformUserLanguageEntity.builder().build();
    platformUserLanguageEntity.setPlatformUser(platformUserEntity);

    when(platformUserRepository.findByAlias(alias))
        .thenReturn(Optional.of(platformUserEntity));
    when(platformUserLanguageRepository.findById(entityId))
        .thenReturn(Optional.of(platformUserLanguageEntity));
    when(platformUserLanguageRepository.save(platformUserLanguageEntity))
        .thenReturn(platformUserLanguageEntity);
    when(persistence2ModelMapper.toPlatformUserLanguage(platformUserLanguageEntity)).thenReturn(
        platformUserLanguage);

    platformUserLanguageService.createOrUpdate(alias, entityId, platformUserLanguage).get();

    verify(platformUserRepository, times(1)).findByAlias(alias);
    verify(service2PersistenceMapper, times(1)).toPlatformUserLanguageEntity(any(), any());
    verify(platformUserLanguageRepository, times(1)).save(any());
    verify(persistence2ModelMapper, times(1)).toPlatformUserLanguage(platformUserLanguageEntity);
  }
}
