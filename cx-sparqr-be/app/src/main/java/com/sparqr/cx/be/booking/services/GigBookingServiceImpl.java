package com.sparqr.cx.be.booking.services;

import com.sparqr.cx.booking.api.dtos.inbound.CreateBookingDto;
import com.sparqr.cx.booking.api.dtos.outbound.GigBookingDto;
import com.sparqr.cx.booking.client.proxies.GigBookingProxyAsync;
import com.sparqr.cx.gig.client.proxies.GigProxyAsync;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GigBookingServiceImpl implements GigBookingService {

  private final GigProxyAsync gigProxyAsync;
  private final GigBookingProxyAsync gigBookingProxyAsync;

  @Override
  public CompletableFuture<GigBookingDto> placeBooking(Long gigId, CreateBookingDto createBookingDto) {

    return CompletableFuture.supplyAsync(() ->
            gigProxyAsync.validateById(gigId))
        .thenCompose(e -> gigBookingProxyAsync.canPlaceBooking(gigId))
        .thenCompose(e -> gigBookingProxyAsync.placeBooking(gigId, createBookingDto));
  }

  @Override
  public CompletableFuture<Boolean> canPlaceBooking(Long gigId) {
    return gigBookingProxyAsync.canPlaceBooking(gigId);
  }
}
