package com.sparqr.cx.iam.services;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.persistence.repositories.PlatformSsoRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.aws.AwsCognitoClient;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class PlatformUserServiceImplTest {

  private PlatformUserService platformUserService;

  @Mock
  private PlatformUserRepository platformUserRepository;

  @Mock
  private PlatformUserProfileService platformUserProfileService;

  @Mock
  private PlatformSsoRepository platformSsoRepository;

  @Mock
  private Service2PersistenceMapper service2PersistenceMapper;

  @Mock
  private Persistence2ModelMapper persistence2ModelMapper;

  @Mock
  private AwsCognitoClient awsCognitoClient;

  @Mock
  private Executor executor;

  @BeforeEach
  void setup() {

    platformUserService = new PlatformUserServiceImpl(platformUserRepository, platformUserProfileService,
        platformSsoRepository, service2PersistenceMapper, persistence2ModelMapper, awsCognitoClient, executor);
  }

  @Test
  void shouldThrowExceptionWhenOldAliasIsNull() {

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> platformUserService.updateAlias(null, "new"));
  }

  @Test
  void shouldThrowExceptionWhenNewAliasIsNull() {

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> platformUserService.updateAlias("old", null));
  }
}