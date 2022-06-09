package com.sparqr.cx.booking.web.controllers;

import com.sparqr.cx.booking.api.EndpointConstants;
import com.sparqr.cx.booking.api.dtos.inbound.CreateBookingDto;
import com.sparqr.cx.booking.api.dtos.outbound.GigBookingDto;
import com.sparqr.cx.booking.mappers.Service2WebMapper;
import com.sparqr.cx.booking.mappers.Web2ServiceMapper;
import com.sparqr.cx.booking.services.GigBookingService;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final Web2ServiceMapper web2ServiceMapper;
  private final Service2WebMapper service2WebMapper;

  @GetMapping(
      path = EndpointConstants.PATH_SERVICES,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<Void> list() {
    return null;
  }

  @GetMapping(
      path = EndpointConstants.PATH_GIG_BOOKINGS_CAN_ORDER,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<Boolean> canPlaceBooking(@PathVariable(name = "gigId") @Valid @NotNull Long gigId) {
    return gigBookingService.canPlaceBooking(gigId);
  }

  @PostMapping(
      path = EndpointConstants.PATH_GIG_BOOKINGS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<GigBookingDto> placeBooking(@PathVariable(name = "gigId") @Valid @NotNull Long gigId,
      @RequestBody @Valid @NotNull CreateBookingDto createBookingDto) {
    return gigBookingService.placeBooking(gigId, web2ServiceMapper.toCreateBooking(createBookingDto))
        .thenApply(service2WebMapper::toGigBookingDto);
  }
}