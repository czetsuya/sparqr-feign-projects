package com.sparqr.cx.gig.mappers;

import com.sparqr.cx.gig.api.dtos.inbound.UpsertGigDto;
import com.sparqr.cx.gig.persistence.entities.GigEntity;
import com.sparqr.cx.gig.persistence.entities.GigGalleryItemEntity;
import com.sparqr.cx.gig.persistence.entities.GigPackageEntity;
import com.sparqr.cx.gig.services.pojos.Gig;
import com.sparqr.cx.gig.services.pojos.GigGalleryItem;
import com.sparqr.cx.gig.services.pojos.GigPackage;
import com.sparqr.cx.gig.services.pojos.UpsertGig;
import java.time.LocalDateTime;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {
    LocalDateTime.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy =
    NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface Service2PersistenceMapper extends LocalDateMapper {

  @Mapping(target = "id", ignore = true)
  void toGigEntity(Gig gig, @MappingTarget GigEntity gigEntity);

  @Mapping(target = "id", ignore = true)
  void toGigEntity(UpsertGig gig, @MappingTarget GigEntity gigEntity);

  @Mapping(target = "id", ignore = true)
  GigEntity toGigEntity(Gig gig);

  @Mapping(target = "id", ignore = true)
  void toGigGalleryItemEntity(GigGalleryItem galleryItem, @MappingTarget GigGalleryItemEntity gigEntity);

  @Mapping(target = "id", ignore = true)
  GigGalleryItemEntity toGigGalleryItemEntity(GigGalleryItem gigGalleryItem);

  @Mapping(target = "id", ignore = true)
  GigPackageEntity toGigPackageEntity(GigPackage gigPackage, @MappingTarget GigPackageEntity gigPackageEntity);

  @Mapping(target = "id", ignore = true)
  GigPackageEntity toGigPackageEntity(GigPackage gigPackage);

  GigEntity toGigEntity(UpsertGig gig);
}