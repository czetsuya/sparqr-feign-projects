package com.sparqr.cx.booking.services;

import com.sparqr.cx.booking.api.commons.GigBookingStatusEnum;
import com.sparqr.cx.booking.mappers.Persistence2ServiceMapper;
import com.sparqr.cx.booking.mappers.Service2PersistenceMapper;
import com.sparqr.cx.booking.persistence.entities.GigBookingEntity;
import com.sparqr.cx.booking.persistence.repositories.GigBookingRepository;
import com.sparqr.cx.booking.services.exceptions.GigMaxBookingReachedException;
import com.sparqr.cx.booking.services.pojos.CreateBooking;
import com.sparqr.cx.booking.services.pojos.GigBooking;
import com.sparqr.cx.gig.client.proxies.GigProxyAsync;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GigBookingServiceImpl implements GigBookingService {

  private final GigBookingRepository gigBookingRepository;
  private final Service2PersistenceMapper service2PersistenceMapper;
  private final Persistence2ServiceMapper persistence2ServiceMapper;
  private final GigProxyAsync gigProxyAsync;

  @Override
  public CompletableFuture<Boolean> canPlaceBooking(Long gigId) {

    return CompletableFuture.supplyAsync(() -> gigBookingRepository.countGigBookingByIdAndStatuses(gigId,
            Set.of(GigBookingStatusEnum.APPROVED)))
        .thenCombine(gigProxyAsync.findById(gigId), (gigBookingCount, gigData) -> {
              if (gigBookingCount.intValue() == gigData.getMaxBooking()) {
                throw new GigMaxBookingReachedException(gigId, gigData.getMaxBooking());
              }
              return Boolean.TRUE;
            }
        );
  }

  @Override
  public CompletableFuture<GigBooking> placeBooking(Long gigId, CreateBooking createBooking) {

    return CompletableFuture.supplyAsync(
        () -> {
          GigBookingEntity gigBookingEntity = service2PersistenceMapper.toGigBookingEntity(createBooking);
          gigBookingEntity.setStatus(GigBookingStatusEnum.PLACED);
          return persistence2ServiceMapper.toGigBooking(
              gigBookingRepository.save(gigBookingEntity));
        }
    );
  }
}
