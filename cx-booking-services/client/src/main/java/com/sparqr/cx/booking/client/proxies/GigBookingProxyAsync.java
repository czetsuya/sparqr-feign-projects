package com.sparqr.cx.booking.client.proxies;

import com.sparqr.cx.booking.api.dtos.inbound.CountGigBooking;
import com.sparqr.cx.booking.api.dtos.inbound.CreateBookingDto;
import com.sparqr.cx.booking.api.dtos.outbound.GigBookingDto;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GigBookingProxyAsync {

  private final GigBookingProxy gigBookingProxy;

  @Async
  public CompletableFuture<Long> countBookingByStatus(@Valid @NotNull Long id,
      CountGigBooking countGigBooking) {
    return CompletableFuture.completedFuture(gigBookingProxy.countBookingByStatus(id, countGigBooking));
  }

  @Async
  public CompletableFuture<Boolean> canPlaceBooking(@Valid @NotNull Long gigId) {
    return CompletableFuture.completedFuture(gigBookingProxy.canPlaceBooking(gigId));
  }

  @Async
  public CompletableFuture<GigBookingDto> placeBooking(Long gigId, @Valid @NotNull CreateBookingDto createBookingDto) {
    return CompletableFuture.completedFuture(gigBookingProxy.placeBooking(gigId, createBookingDto));
  }
}
