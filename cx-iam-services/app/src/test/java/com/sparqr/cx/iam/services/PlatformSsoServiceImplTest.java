package com.sparqr.cx.iam.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparqr.cx.iam.api.dtos.inbound.MapUpdateOrCreateIfAbsentRequest;
import com.sparqr.cx.iam.api.dtos.outbound.PlatformUserDto;
import com.sparqr.cx.iam.fixtures.PlatformSsoFixtures;
import com.sparqr.cx.iam.mappers.Persistence2ModelMapper;
import com.sparqr.cx.iam.mappers.Persistence2ModelMapperImpl;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapper;
import com.sparqr.cx.iam.mappers.Service2PersistenceMapperImpl;
import com.sparqr.cx.iam.mappers.Service2WebMapper;
import com.sparqr.cx.iam.mappers.Service2WebMapperImpl;
import com.sparqr.cx.iam.mappers.Web2ServiceMapper;
import com.sparqr.cx.iam.mappers.Web2ServiceMapperImpl;
import com.sparqr.cx.iam.persistence.entities.PlatformSsoEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformSsoRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.aws.AwsCognitoClient;
import com.sparqr.cx.iam.services.pojos.PlatformUser;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class PlatformSsoServiceImplTest {

  private PlatformSsoService platformSsoService;

  @Mock
  private PlatformSsoRepository platformSsoRepository;

  @Mock
  private PlatformUserRepository platformUserRepository;

  @Mock
  private AwsCognitoClient awsCognitoClient;

  private Persistence2ModelMapper persistence2ModelMapper;
  private Service2PersistenceMapper service2PersistenceMapper;
  private Web2ServiceMapper web2ServiceMapper;
  private Service2WebMapper service2WebMapper;
  private Executor executor;

  @BeforeEach
  void setup() {

    persistence2ModelMapper = new Persistence2ModelMapperImpl();
    service2PersistenceMapper = new Service2PersistenceMapperImpl();
    web2ServiceMapper = new Web2ServiceMapperImpl();
    service2WebMapper = new Service2WebMapperImpl();
    executor = Executors.newSingleThreadExecutor();

    platformSsoService = new PlatformSsoServiceImpl(platformSsoRepository, platformUserRepository,
        persistence2ModelMapper, service2PersistenceMapper, executor, awsCognitoClient);
  }

  /**
   * UC1: External ref exists and user is mapped.
   */
  @Test
  @SneakyThrows
  void Should_FindPlatformUser_Given_ItExistsInOpenIdAndMapped() {

    MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest = PlatformSsoFixtures.getPlatformUserInboundDto();

    mockNonEmptyPlatformOpenId(mapUpdateOrCreateIfAbsentRequest);

    PlatformUser platformUser = web2ServiceMapper.toUserDetail(mapUpdateOrCreateIfAbsentRequest);

    PlatformUser platformUserResp = platformSsoService.createIfNotExists(platformUser).get();

    PlatformUserDto platformUserOutDto =
        service2WebMapper.toPlatformUserDto(platformUserResp);

    assertNonEmptyPlatformOpenId(platformUserOutDto,
        PlatformSsoFixtures.getPlatformOpenId(), PlatformSsoFixtures.getPlatformUserEntity());
  }

  /**
   * UC2: External ref exists and is not mapped. This case should not happen.
   */
  @Test
  @SneakyThrows
  void Should_CreatePlatformUser_Given_ItOnlyExistsInSso() {

    MapUpdateOrCreateIfAbsentRequest platformOpenId = PlatformSsoFixtures.getPlatformUserInboundDto();

    Optional<PlatformSsoEntity> optPlatformOpenId = Optional.ofNullable(PlatformSsoFixtures.getPlatformOpenId());
    when(platformSsoRepository.findByExternalRef(platformOpenId.getExternalRef())).thenReturn(optPlatformOpenId);
    when(platformUserRepository.save(any())).thenReturn(PlatformSsoFixtures.getPlatformUserEntity());
    when(platformSsoRepository.save(any())).thenReturn(PlatformSsoFixtures.getPlatformOpenId());

    PlatformUser platformUser = web2ServiceMapper.toUserDetail(platformOpenId);

    PlatformUser platformUserOut = platformSsoService.createIfNotExists(platformUser).get();

    PlatformUserDto platformUserDto =
        service2WebMapper.toPlatformUserDto(platformUserOut);

    assertNonEmptyPlatformOpenId(platformUserDto,
        PlatformSsoFixtures.getPlatformOpenId(), PlatformSsoFixtures.getPlatformUserEntity());
    verify(platformUserRepository, times(2)).save(any());
    verify(platformSsoRepository, times(1)).save(any());
  }

  /**
   * UC3: External ref does not exist but email does in platform user table
   */
  @Test
  @SneakyThrows
  void Should_CreateOpenIdEntry_Given_EmailExistsAsUsernameInPlatformUser() {

    MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest = PlatformSsoFixtures.getPlatformUserInboundDto();

    when(platformSsoRepository.findByExternalRef(mapUpdateOrCreateIfAbsentRequest.getExternalRef())).thenReturn(
        Optional.empty());
    when(platformUserRepository.findByEmail(PlatformSsoFixtures.EMAIL)).thenReturn(
        Optional.ofNullable(PlatformSsoFixtures.getPlatformUserEntity()));

    PlatformUser platformUser = web2ServiceMapper.toUserDetail(mapUpdateOrCreateIfAbsentRequest);

    PlatformUser platformUserOutbound = platformSsoService.createIfNotExists(platformUser).get();

    PlatformUserDto platformUserOutDto =
        service2WebMapper.toPlatformUserDto(platformUserOutbound);

    assertNonEmptyPlatformOpenId(platformUserOutDto,
        PlatformSsoFixtures.getPlatformOpenId(), PlatformSsoFixtures.getPlatformUserEntity());

    verify(platformSsoRepository, times(1)).save(any());
  }

  /**
   * UC4: External ref does not exist in openid table and email does not exist in app user table as well. Create an
   * entry in both openid and app user table.
   */
  @Test
  @SneakyThrows
  void Should_CreateSsoAndPlatformUser_Given_ExternalRefAndEmailDontExists() {

    MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest = PlatformSsoFixtures.getPlatformUserInboundDto();

    when(platformSsoRepository.findByExternalRef(mapUpdateOrCreateIfAbsentRequest.getExternalRef())).thenReturn(
        Optional.empty());
    when(platformUserRepository.findByEmail(PlatformSsoFixtures.EMAIL)).thenReturn(Optional.empty());
    when(platformUserRepository.save(any())).thenReturn(PlatformSsoFixtures.getPlatformUserEntity());

    PlatformUser platformUser = web2ServiceMapper.toUserDetail(mapUpdateOrCreateIfAbsentRequest);

    PlatformUser platformUserOutbound = platformSsoService.createIfNotExists(platformUser).get();

    PlatformUserDto platformUserOutDto =
        service2WebMapper.toPlatformUserDto(platformUserOutbound);

    assertNonEmptyPlatformOpenId(platformUserOutDto,
        PlatformSsoFixtures.getPlatformOpenId(), PlatformSsoFixtures.getPlatformUserEntity());

    verify(platformSsoRepository, times(1)).save(any());
    verify(platformUserRepository, times(2)).save(any());
  }

  private void assertNonEmptyPlatformOpenId(PlatformUserDto platformUserDto,
      PlatformSsoEntity platformOpenId, PlatformUserEntity platformUser) {

    assertThat(platformUserDto.getExternalRef()).isEqualTo(platformOpenId.getExternalRef());
    assertThat(platformUserDto.getEmail()).isEqualTo(platformUser.getEmail());
    assertThat(platformUserDto.getFirstName()).isEqualTo(platformUser.getFirstName());
    assertThat(platformUserDto.getLastName()).isEqualTo(platformUser.getLastName());
    if (platformUser.getGender() != null) {
      assertThat(platformUserDto.getGender()).isEqualTo(platformUser.getGender().name());
    }
    assertThat(platformUserDto.getContactNo()).isEqualTo(platformUser.getContactNo());
    assertThat(platformUserDto.getDob()).isEqualTo(platformUser.getDob());
  }

  private void mockEmptyPlatformOpenId(MapUpdateOrCreateIfAbsentRequest platformOpenId) {

    Optional<PlatformSsoEntity> optPlatformOpenId = Optional.empty();
    when(platformSsoRepository.findByExternalRef(platformOpenId.getExternalRef())).thenReturn(optPlatformOpenId);
    when(platformUserRepository.save(any())).thenReturn(PlatformSsoFixtures.getPlatformUserEntity());
  }

  private void mockNonEmptyPlatformOpenId(MapUpdateOrCreateIfAbsentRequest platformOpenId) {

    Optional<PlatformSsoEntity> optPlatformOpenId = Optional.ofNullable(PlatformSsoFixtures.getPlatformOpenId());
    optPlatformOpenId.ifPresent(openId -> openId.setPlatformUser(PlatformSsoFixtures.getPlatformUserEntity()));
    when(platformSsoRepository.findByExternalRef(platformOpenId.getExternalRef())).thenReturn(optPlatformOpenId);
  }
}