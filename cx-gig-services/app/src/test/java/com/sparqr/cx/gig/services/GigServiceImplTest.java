package com.sparqr.cx.gig.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparqr.cx.gig.api.commons.GigStatusEnum;
import com.sparqr.cx.gig.api.dtos.inbound.UpsertGigDto;
import com.sparqr.cx.gig.mappers.Persistence2ModelMapper;
import com.sparqr.cx.gig.mappers.Service2PersistenceMapper;
import com.sparqr.cx.gig.persistence.entities.GigEntity;
import com.sparqr.cx.gig.persistence.repositories.GigRepository;
import com.sparqr.cx.gig.services.exceptions.GigNotActiveException;
import com.sparqr.cx.gig.services.exceptions.GigNotFoundException;
import com.sparqr.cx.gig.services.pojos.Gig;
import com.sparqr.cx.gig.services.pojos.UpsertGig;
import java.util.Arrays;
import java.util.List;
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
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class GigServiceImplTest {

  private GigService gigService;

  @Mock
  private GigRepository gigRepository;

  @Mock
  private Service2PersistenceMapper service2PersistenceMapper;

  @Mock
  private Persistence2ModelMapper persistence2ModelMapper;

  @BeforeEach
  void setUp() {
    gigService = new GigServiceImpl(gigRepository, service2PersistenceMapper, persistence2ModelMapper);
  }

  @SneakyThrows
  @Test
  void shouldThrowAnExceptionWhenIdAndUserIdIsNull() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
            () -> gigService.createOrUpdate(null, null, UpsertGig.builder().build()).get()
        )
        .withMessage("userId cannot be null");
  }

  @SneakyThrows
  @Test
  void shouldThrowAnExceptionWhenGigIsNull() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
            () -> gigService.createOrUpdate(1L, 1L, null).get()
        )
        .withMessage("gig cannot be null");
  }

  @SneakyThrows
  @Test
  void shouldSaveWhenGigIdIsNull() {

    Long userId = 1L;
    UpsertGig gig = UpsertGig.builder().build();
    GigEntity gigEntity = GigEntity.builder().build();

    when(service2PersistenceMapper.toGigEntity(gig)).thenReturn(gigEntity);
    gigEntity.setUserId(userId);
    when(gigRepository.save(gigEntity)).thenReturn(gigEntity);

    gigService.createOrUpdate(userId, null, gig).get();

    verify(service2PersistenceMapper, times(1)).toGigEntity(gig);
    verify(gigRepository, times(1)).save(gigEntity);
    verify(persistence2ModelMapper, times(1)).toGig(gigEntity);
  }

  @SneakyThrows
  @Test
  void shouldUpdateWhenGigIdIsNotNull() {

    Long gigId = 1L;
    Long userId = 1L;
    UpsertGig gig = UpsertGig.builder().build();
    GigEntity gigEntity = GigEntity.builder().build();

    when(gigRepository.findById(gigId)).thenReturn(Optional.of(gigEntity));
    when(gigRepository.save(gigEntity)).thenReturn(gigEntity);

    gigService.createOrUpdate(userId, gigId, gig).get();

    verify(gigRepository, times(1)).findById(gigId);
    verify(gigRepository, times(1)).save(gigEntity);
    verify(persistence2ModelMapper, times(1)).toGig(gigEntity);
    verify(service2PersistenceMapper, times(1)).toGigEntity(gig, gigEntity);
  }

  @Test
  @SneakyThrows
  void shouldDeleteGivenAnId() {

    Long gigId = 1L;
    gigService.delete(gigId).get();

    verify(gigRepository, times(1)).deleteById(gigId);
  }

  @SneakyThrows
  @Test
  void shouldListOk() {

    Long userId = 1L;

    List<GigEntity> gigEntities = Arrays.asList(
        GigEntity.builder().id(1L).build(),
        GigEntity.builder().id(2L).build()
    );
    List<Gig> gigs = Arrays.asList(
        Gig.builder().id(1L).build(),
        Gig.builder().id(2L).build()
    );

    when(gigRepository.findByUserId(userId)).thenReturn(gigEntities);
    when(persistence2ModelMapper.toGig(gigEntities)).thenReturn(gigs);

    List<Gig> gigsResult = gigService.list(userId).get();
    assertThat(gigsResult.size()).isEqualTo(2);
  }

  @Test
  void shouldHandleGigNotFoundException() {

    Long gigId = 1L;
    when(gigRepository.findById(gigId)).thenReturn(Optional.empty());

    assertThatExceptionOfType(GigNotFoundException.class)
        .isThrownBy(() -> gigService.validateById(gigId).get());
  }

  @Test
  void shouldRejectInactiveGigWhenValidating() {

    Long gigId = 1L;
    GigEntity gigEntity = GigEntity.builder()
        .id(gigId)
        .status(GigStatusEnum.DRAFT)
        .build();

    when(gigRepository.findById(gigId)).thenReturn(Optional.of(gigEntity));

    assertThatExceptionOfType(GigNotActiveException.class)
        .isThrownBy(() -> gigService.validateById(gigId).get());
  }

  @SneakyThrows
  @Test
  void shouldValidatedActiveGig() {

    Long gigId = 1L;
    GigEntity gigEntity = GigEntity.builder()
        .id(gigId)
        .status(GigStatusEnum.ACTIVE)
        .build();

    when(gigRepository.findById(gigId)).thenReturn(Optional.of(gigEntity));

    assertThat(gigService.validateById(gigId).get()).isEqualTo(Boolean.TRUE);
  }

  @Test
  void shouldThrowAnErrorWhenIdDoesNotExists() {

    Long gigId = 1L;
    when(gigRepository.findById(gigId)).thenReturn(Optional.empty());

    assertThatExceptionOfType(ExecutionException.class)
        .isThrownBy(() -> gigService.findById(gigId).get())
        .withMessage("com.sparqr.cx.gig.services.exceptions.GigNotFoundException");
  }

  @SneakyThrows
  @Test
  void shouldReturnGigIfExistsInDb() {

    Long gigId = 1L;
    GigEntity gigEntity = GigEntity.builder()
        .id(gigId)
        .status(GigStatusEnum.DRAFT)
        .build();
    Gig gig = Gig.builder()
        .id(gigId)
        .build();

    when(gigRepository.findById(gigId)).thenReturn(Optional.of(gigEntity));
    when(persistence2ModelMapper.toGig(gigEntity)).thenReturn(gig);

    assertThat(gigService.findById(gigId).get()).isEqualTo(gig);
  }

  @Test
  void shouldThrowAnExceptionWhenGigNotFoundDuringUpdateStatus() {

    Long gigId = 1L;
    when(gigRepository.findById(gigId)).thenReturn(Optional.empty());

    assertThatExceptionOfType(ExecutionException.class)
        .isThrownBy(() -> gigService.updateStatus(gigId, GigStatusEnum.DRAFT).get())
        .withMessage("com.sparqr.cx.gig.services.exceptions.GigNotFoundException");
  }

  @SneakyThrows
  @Test
  void shouldUpdateStatusOk() {

    Long gigId = 1L;
    GigEntity gigEntity = GigEntity.builder()
        .id(gigId)
        .status(GigStatusEnum.ACTIVE)
        .build();

    when(gigRepository.findById(gigId)).thenReturn(Optional.of(gigEntity));

    gigService.updateStatus(gigId, GigStatusEnum.PAUSE).get();

    gigEntity.setStatus(GigStatusEnum.PAUSE);
    verify(gigRepository, times(1)).save(gigEntity);
  }
}