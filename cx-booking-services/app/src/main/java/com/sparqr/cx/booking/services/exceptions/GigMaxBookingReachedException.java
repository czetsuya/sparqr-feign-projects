package com.sparqr.cx.booking.services.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GigMaxBookingReachedException extends RuntimeException {

  private Long gigId;
  private Integer maxBooking;

  public GigMaxBookingReachedException(Long gigId, Integer maxBooking) {
    this.gigId = gigId;
    this.maxBooking = maxBooking;
  }
}
