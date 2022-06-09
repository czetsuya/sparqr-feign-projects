package com.sparqr.cx.iam.mappers;

import com.sparqr.cx.iam.persistence.entities.PlatformUserCertificationEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEducationEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserLanguageEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserProfileEntity;
import com.sparqr.cx.iam.services.pojos.PlatformUser;
import com.sparqr.cx.iam.services.pojos.PlatformUserCertification;
import com.sparqr.cx.iam.services.pojos.PlatformUserEducation;
import com.sparqr.cx.iam.services.pojos.PlatformUserLanguage;
import com.sparqr.cx.iam.services.pojos.PlatformUserProfile;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {
    LocalDateTime.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface Persistence2ModelMapper extends LocalDateMapper {

  PlatformUser toPlatformUser(PlatformUserEntity platformUserEntity);

  void toPlatformUser(PlatformUserEntity platformUserEntity, @MappingTarget PlatformUser platformUser);

  PlatformUserProfile toPlatformUserProfile(PlatformUserProfileEntity platformUserProfileEntity);

  PlatformUserCertification toPlatformUserCertification(
      PlatformUserCertificationEntity platformUserCertificationEntity);

  List<PlatformUserCertification> toPlatformUserCertification(
      List<PlatformUserCertificationEntity> platformUserCertificationEntity);

  List<PlatformUserLanguage> toPlatformUserLanguage(List<PlatformUserLanguageEntity> platformUserLanguageEntities);

  PlatformUserLanguage toPlatformUserLanguage(PlatformUserLanguageEntity platformUserLanguageEntity);

  List<PlatformUserEducation> toPlatformUserEducation(List<PlatformUserEducationEntity> platformUserEducationEntities);

  PlatformUserEducation toPlatformUserEducation(PlatformUserEducationEntity platformUserEducationEntity);
}
