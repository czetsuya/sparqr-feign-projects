package com.sparqr.cx.booking.mappers;

import com.sparqr.cx.booking.api.dtos.inbound.CreateBookingDto;
import com.sparqr.cx.booking.services.pojos.CreateBooking;
import java.time.LocalDateTime;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {
    LocalDateTime.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface Web2ServiceMapper extends LocalDateMapper {

  CreateBooking toCreateBooking(CreateBookingDto createBookingDto);
}