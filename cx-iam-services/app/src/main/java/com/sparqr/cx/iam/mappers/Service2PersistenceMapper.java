package com.sparqr.cx.iam.mappers;

import com.sparqr.cx.iam.persistence.entities.PlatformSsoEntity;
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
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {
    LocalDateTime.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy =
    NullValuePropertyMappingStrategy.IGNORE)
public interface Service2PersistenceMapper extends LocalDateMapper {

  PlatformUserEntity toPlatformUserEntity(PlatformUser platformUser);

  void toPlatformUserEntity(PlatformUser platformUser, @MappingTarget PlatformUserEntity platformUserEntity);

  @Mapping(target = "platformUser", ignore = true)
  PlatformSsoEntity toPlatformSsoEntity(PlatformUser platformUser);

  PlatformUserProfileEntity toPlatformUserProfileEntity(PlatformUserProfile platformUserProfile);

  void toPlatformUserProfileEntity(PlatformUserProfile platformUserProfile,
      @MappingTarget PlatformUserProfileEntity platformUserProfileEntity);

  void toPlatformUserCertificationEntity(PlatformUserCertification platformUserCertification,
      @MappingTarget PlatformUserCertificationEntity platformUserCertificationEntity);

  PlatformUserCertificationEntity toPlatformUserCertificationEntity(
      PlatformUserCertification platformUserCertification);

  PlatformUserLanguageEntity toPlatformUserLanguageEntity(PlatformUserLanguage platformUserLanguage);

  void toPlatformUserLanguageEntity(PlatformUserLanguage platformUserLanguage,
      @MappingTarget PlatformUserLanguageEntity platformUserLanguageEntity);

  void toPlatformUserEducationEntity(PlatformUserEducation platformUserEducation,
      @MappingTarget PlatformUserEducationEntity platformUserEducationEntity);

  PlatformUserEducationEntity toPlatformUserEducationEntity(PlatformUserEducation platformUserEducation);
}