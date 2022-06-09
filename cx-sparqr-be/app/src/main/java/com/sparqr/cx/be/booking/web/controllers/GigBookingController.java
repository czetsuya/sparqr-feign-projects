package com.sparqr.cx.be.booking.web.controllers;

import static com.sparqr.cx.be.iam.EndpointConstants.PATH_GIG_BOOKINGS;

import com.sparqr.cx.be.booking.services.GigBookingService;
import com.sparqr.cx.booking.api.dtos.inbound.CreateBookingDto;
import com.sparqr.cx.booking.api.dtos.outbound.GigBookingDto;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class GigBookingController {

  private final GigBookingService gigBookingService;

  @PostMapping(
      path = PATH_GIG_BOOKINGS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<GigBookingDto> placeBooking(@PathVariable(name = "gigId") @Valid @NotNull Long gigId,
      @RequestBody @Valid @NotNull CreateBookingDto createBookingDto) {

    return gigBookingService.placeBooking(gigId, createBookingDto);
  }
}
