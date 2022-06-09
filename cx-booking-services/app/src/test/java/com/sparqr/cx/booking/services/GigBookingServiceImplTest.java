package com.sparqr.cx.booking.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparqr.cx.booking.api.commons.GigBookingStatusEnum;
import com.sparqr.cx.booking.mappers.Persistence2ServiceMapper;
import com.sparqr.cx.booking.mappers.Service2PersistenceMapper;
import com.sparqr.cx.booking.persistence.entities.GigBookingEntity;
import com.sparqr.cx.booking.persistence.repositories.GigBookingRepository;
import com.sparqr.cx.booking.services.pojos.CreateBooking;
import com.sparqr.cx.booking.services.pojos.GigBooking;
import com.sparqr.cx.gig.api.dtos.outbound.GigDto;
import com.sparqr.cx.gig.client.proxies.GigProxyAsync;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
class GigBookingServiceImplTest {

  private GigBookingService gigBookingService;

  @Mock
  private GigBookingRepository gigBookingRepository;

  @Mock
  private Service2PersistenceMapper service2PersistenceMapper;

  @Mock
  private Persistence2ServiceMapper persistence2ServiceMapper;

  @Mock
  private GigProxyAsync gigProxyAsync;

  @BeforeEach
  void setUp() {
    gigBookingService = new GigBookingServiceImpl(gigBookingRepository, service2PersistenceMapper,
        persistence2ServiceMapper, gigProxyAsync);
  }

  @SneakyThrows
  @Test
  void canPlaceBookingOk() {

    Long gigId = 1L;
    Long gigBookingCount = 3L;
    GigDto gigDto = GigDto.builder()
        .maxBooking(5)
        .build();

    when(gigBookingRepository.countGigBookingByIdAndStatuses(gigId,
        Set.of(GigBookingStatusEnum.APPROVED))).thenReturn(gigBookingCount);
    when(gigProxyAsync.findById(gigId)).thenReturn(CompletableFuture.completedFuture(gigDto));

    assertThat(gigBookingService.canPlaceBooking(gigId).get()).isEqualTo(Boolean.TRUE);
  }

  @SneakyThrows
  @Test
  void canPlaceBookingKoMaxBookingReached() {

    Long gigId = 1L;
    Long gigBookingCount = 5L;
    GigDto gigDto = GigDto.builder()
        .maxBooking(5)
        .build();

    when(gigBookingRepository.countGigBookingByIdAndStatuses(gigId,
        Set.of(GigBookingStatusEnum.APPROVED))).thenReturn(gigBookingCount);
    when(gigProxyAsync.findById(gigId)).thenReturn(CompletableFuture.completedFuture(gigDto));

    assertThatExceptionOfType(ExecutionException.class)
        .isThrownBy(() -> {
          gigBookingService.canPlaceBooking(gigId).get();
        })
        .withMessage("com.sparqr.cx.booking.services.exceptions.GigMaxBookingReachedException");
  }

  @SneakyThrows
  @Test
  void placeBookingOk() {

    Long gigId = 1L;

    CreateBooking createBooking = CreateBooking.builder()
        .bookerId(1L)
        .gigPackageId(1L)
        .build();
    GigBookingEntity gigBookingEntity = GigBookingEntity.builder()
        .bookerId(1L)
        .gigPackageId(1L)
        .build();
    GigBooking gigBooking = GigBooking.builder()
        .bookerId(1L)
        .gigPackageId(1L)
        .build();

    when(service2PersistenceMapper.toGigBookingEntity(createBooking)).thenReturn(gigBookingEntity);
    when(gigBookingRepository.save(gigBookingEntity)).thenReturn(gigBookingEntity);
    when(persistence2ServiceMapper.toGigBooking(gigBookingEntity)).thenReturn(gigBooking);

    gigBookingService.placeBooking(gigId, createBooking).get();

    verify(service2PersistenceMapper, times(1)).toGigBookingEntity(createBooking);
    verify(gigBookingRepository, times(1)).save(gigBookingEntity);
    verify(persistence2ServiceMapper, times(1)).toGigBooking(gigBookingEntity);
  }
}