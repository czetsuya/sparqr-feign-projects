package com.sparqr.cx.gig.mappers;

import com.sparqr.cx.gig.api.dtos.outbound.GigDto;
import com.sparqr.cx.gig.api.dtos.commons.GigGalleryItemDto;
import com.sparqr.cx.gig.api.dtos.commons.GigPackageDto;
import com.sparqr.cx.gig.services.pojos.Gig;
import com.sparqr.cx.gig.services.pojos.GigGalleryItem;
import com.sparqr.cx.gig.services.pojos.GigPackage;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {
    LocalDateTime.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface Service2WebMapper extends LocalDateMapper {

  List<GigDto> toGigDto(List<Gig> gigs);

  GigDto toGigDto(Gig gig);

  List<GigGalleryItemDto> toGigGalleryItemDto(List<GigGalleryItem> gigGalleryItems);

  GigGalleryItemDto toGigGalleryItemDto(GigGalleryItem gigGalleryItem);

  GigPackageDto toGigPackageDto(GigPackage gigPackage);

  List<GigPackageDto> toGigPackageDto(List<GigPackage> gigPackages);
}
