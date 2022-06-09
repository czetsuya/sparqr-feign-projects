package com.sparqr.cx.gig.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparqr.cx.gig.mappers.Persistence2ModelMapper;
import com.sparqr.cx.gig.mappers.Service2PersistenceMapper;
import com.sparqr.cx.gig.persistence.entities.GigEntity;
import com.sparqr.cx.gig.persistence.entities.GigPackageEntity;
import com.sparqr.cx.gig.persistence.repositories.GigPackageRepository;
import com.sparqr.cx.gig.persistence.repositories.GigRepository;
import com.sparqr.cx.gig.services.pojos.GigPackage;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class GigPackageServiceImplTest {

  private GigPackageService gigPackageService;

  @Mock
  private GigRepository gigRepository;

  @Mock
  private GigPackageRepository gigPackageRepository;

  @Mock
  private Persistence2ModelMapper persistence2ModelMapper;

  @Mock
  private Service2PersistenceMapper service2PersistenceMapper;

  @BeforeEach
  void setUp() {

    gigPackageService = new GigPackageServiceImpl(gigRepository, gigPackageRepository,
        persistence2ModelMapper, service2PersistenceMapper);
  }

  @Test
  @SneakyThrows
  void shouldThrowAnExceptionWhenIdAndGigIdIsNull() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> gigPackageService.createOrUpdate(null, null, GigPackage.builder().build()).get())
        .withMessage("gigId cannot be null");
  }

  @Test
  @SneakyThrows
  void shouldThrowAnExceptionWhenPackageIsNull() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> gigPackageService.createOrUpdate(1L, 1L, null))
        .withMessage("gigPackage cannot be null");
  }

  @Test
  @SneakyThrows
  void shouldSaveWhenPackageIdIsNull() {

    Long gigId = 1L;
    GigEntity gigEntity = GigEntity.builder().build();
    GigPackage Package = GigPackage.builder().build();
    GigPackageEntity gigPackageEntity = GigPackageEntity.builder().build();

    when(gigRepository.findById(gigId)).thenReturn(Optional.ofNullable(gigEntity));
    when(service2PersistenceMapper.toGigPackageEntity(Package)).thenReturn(gigPackageEntity);
    gigPackageEntity.setGig(gigEntity);
    when(gigPackageRepository.save(gigPackageEntity)).thenReturn(gigPackageEntity);

    gigPackageService.createOrUpdate(gigId, null, Package).get();

    verify(service2PersistenceMapper, times(1)).toGigPackageEntity(Package);
    verify(gigPackageRepository, times(1)).save(gigPackageEntity);
    verify(persistence2ModelMapper, times(1)).toGigPackage(gigPackageEntity);
  }

  @Test
  @SneakyThrows
  void shouldUpdateWhenPackageIdIsNotNull() {

    Long gigId = 1L;
    Long userId = 1L;
    GigPackage Package = GigPackage.builder().build();
    GigPackageEntity gigPackageEntity = GigPackageEntity.builder().build();

    when(gigPackageRepository.findById(gigId)).thenReturn(Optional.of(gigPackageEntity));
    when(gigPackageRepository.save(gigPackageEntity)).thenReturn(gigPackageEntity);

    gigPackageService.createOrUpdate(userId, gigId, Package).get();

    verify(gigPackageRepository, times(1)).findById(gigId);
    verify(service2PersistenceMapper, times(1)).toGigPackageEntity(any(), any());
    verify(gigPackageRepository, times(1)).save(gigPackageEntity);
    verify(persistence2ModelMapper, times(1)).toGigPackage(gigPackageEntity);
  }

  @Test
  @SneakyThrows
  void shouldDeleteGivenAnId() {

    Long gigId = 1L;
    gigPackageService.delete(gigId).get();

    verify(gigPackageRepository, times(1)).deleteById(gigId);
  }

  @SneakyThrows
  @Test
  void shouldListOk() {

    Long gigId = 1L;
    List<GigPackageEntity> gigPackageEntities = Arrays.asList(
        GigPackageEntity.builder().id(1L).build(),
        GigPackageEntity.builder().id(2L).build()
    );
    List<GigPackage> gigPackages = Arrays.asList(
        GigPackage.builder().id(1L).build(),
        GigPackage.builder().id(2L).build()
    );

    when(gigPackageRepository.findByGigId(gigId)).thenReturn(gigPackageEntities);
    when(persistence2ModelMapper.toGigPackage(gigPackageEntities)).thenReturn(gigPackages);

    List<GigPackage> gigPackagesResult = gigPackageService.list(gigId).get();
    assertThat(gigPackagesResult.size()).isEqualTo(2);
  }
}