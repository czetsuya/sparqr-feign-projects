package com.sparqr.cx.booking.mappers;

import com.sparqr.cx.booking.api.dtos.outbound.GigBookingDto;
import com.sparqr.cx.booking.services.pojos.GigBooking;
import java.time.LocalDateTime;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {
    LocalDateTime.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface Service2WebMapper extends LocalDateMapper {

  GigBookingDto toGigBookingDto(GigBooking gigBooking);
}
