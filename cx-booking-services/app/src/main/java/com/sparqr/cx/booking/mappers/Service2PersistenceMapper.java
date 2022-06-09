package com.sparqr.cx.booking.mappers;

import com.sparqr.cx.booking.persistence.entities.GigBookingEntity;
import com.sparqr.cx.booking.services.pojos.CreateBooking;
import java.time.LocalDateTime;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {
    LocalDateTime.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy =
    NullValuePropertyMappingStrategy.IGNORE)
public interface Service2PersistenceMapper extends LocalDateMapper {

  GigBookingEntity toGigBookingEntity(CreateBooking createBooking);
}