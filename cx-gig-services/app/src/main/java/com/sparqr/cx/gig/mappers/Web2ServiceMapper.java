package com.sparqr.cx.gig.mappers;

import com.sparqr.cx.gig.api.dtos.inbound.UpsertGigDto;
import com.sparqr.cx.gig.api.dtos.outbound.GigDto;
import com.sparqr.cx.gig.api.dtos.commons.GigGalleryItemDto;
import com.sparqr.cx.gig.api.dtos.commons.GigPackageDto;
import com.sparqr.cx.gig.services.pojos.Gig;
import com.sparqr.cx.gig.services.pojos.GigGalleryItem;
import com.sparqr.cx.gig.services.pojos.GigPackage;
import com.sparqr.cx.gig.services.pojos.UpsertGig;
import java.time.LocalDateTime;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {
    LocalDateTime.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface Web2ServiceMapper extends LocalDateMapper {

  @Mapping(target = "id", ignore = true)
  Gig toGig(GigDto gigDto);

  GigGalleryItem toGigGalleryItem(GigGalleryItemDto gigGalleryItemDto);

  GigPackage toGigPackage(GigPackageDto gigPackageDto);

  UpsertGig toGig(UpsertGigDto gigDto);
}
