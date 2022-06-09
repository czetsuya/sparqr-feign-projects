package com.sparqr.cx.gig.mappers;

import com.sparqr.cx.gig.persistence.entities.GigEntity;
import com.sparqr.cx.gig.persistence.entities.GigGalleryItemEntity;
import com.sparqr.cx.gig.persistence.entities.GigPackageEntity;
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
public interface Persistence2ModelMapper extends LocalDateMapper {

  Gig toGig(GigEntity gigEntity);

  List<Gig> toGig(List<GigEntity> byUserId);

  List<GigGalleryItem> toGigGalleryItem(List<GigGalleryItemEntity> gigGalleryItemEntities);

  GigGalleryItem toGigGalleryItem(GigGalleryItemEntity gigGalleryItemEntity);

  GigPackage toGigPackage(GigPackageEntity gigPackageEntity);

  List<GigPackage> toGigPackage(List<GigPackageEntity> gigPackageEntities);
}
