package com.sparqr.cx.be.booking.services;

import com.sparqr.cx.booking.api.dtos.inbound.CreateBookingDto;
import com.sparqr.cx.booking.api.dtos.outbound.GigBookingDto;
import java.util.concurrent.CompletableFuture;

public interface GigBookingService {

  CompletableFuture<GigBookingDto> placeBooking(Long gigId, CreateBookingDto createBookingDto);

  CompletableFuture<Boolean> canPlaceBooking(Long gigId);
}
