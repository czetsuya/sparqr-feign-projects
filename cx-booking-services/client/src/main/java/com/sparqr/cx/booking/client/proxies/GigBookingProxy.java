package com.sparqr.cx.booking.client.proxies;

import com.sparqr.cx.booking.api.EndpointConstants;
import com.sparqr.cx.booking.api.dtos.inbound.CountGigBooking;
import com.sparqr.cx.booking.api.dtos.inbound.CreateBookingDto;
import com.sparqr.cx.booking.api.dtos.outbound.GigBookingDto;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "gigBookingProxy", url = "${app.cx.client.booking-services.url}")
public interface GigBookingProxy {

  @PostMapping(
      path = EndpointConstants.PATH_GIG_BOOKINGS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  Long countBookingByStatus(@PathVariable @Valid @NotNull Long gigId, @RequestBody
      CountGigBooking countGigBooking);

  @GetMapping(
      path = EndpointConstants.PATH_GIG_BOOKINGS_CAN_ORDER,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  Boolean canPlaceBooking(@PathVariable(name = "gigId") @Valid @NotNull Long gigId);

  @PostMapping(
      path = EndpointConstants.PATH_GIG_BOOKINGS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  GigBookingDto placeBooking(@PathVariable(name = "gigId") @Valid @NotNull Long gigId,
      @RequestBody @Valid @NotNull CreateBookingDto createBookingDto);
}
