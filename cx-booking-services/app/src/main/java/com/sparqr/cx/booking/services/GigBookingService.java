package com.sparqr.cx.booking.services;

import com.sparqr.cx.booking.services.pojos.CreateBooking;
import com.sparqr.cx.booking.services.pojos.GigBooking;
import java.util.concurrent.CompletableFuture;

public interface GigBookingService {

  CompletableFuture<Boolean> canPlaceBooking(Long gigId);

  CompletableFuture<GigBooking> placeBooking(Long gigId, CreateBooking createBooking);
}
