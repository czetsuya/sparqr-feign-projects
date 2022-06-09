package com.sparqr.cx.iam.mappers;

import com.sparqr.cx.iam.api.dtos.inbound.CreateOrUpdateUserProfileRequestDto;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserCertificationDto;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserEducationDto;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserLanguageDto;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateUserDetailDto;
import com.sparqr.cx.iam.api.dtos.outbound.PlatformUserDto;
import com.sparqr.cx.iam.api.dtos.outbound.UserDetailDto;
import com.sparqr.cx.iam.services.pojos.PlatformUser;
import com.sparqr.cx.iam.services.pojos.PlatformUserCertification;
import com.sparqr.cx.iam.services.pojos.PlatformUserEducation;
import com.sparqr.cx.iam.services.pojos.PlatformUserLanguage;
import com.sparqr.cx.iam.services.pojos.PlatformUserProfile;
import com.sparqr.cx.iam.services.pojos.UserDetail;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {
    LocalDateTime.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface Service2WebMapper extends LocalDateMapper {

  PlatformUserDto toPlatformUserDto(PlatformUser platformUser);

  CreateOrUpdateUserProfileRequestDto toPlatformUserProfileDto(PlatformUserProfile platformUserProfile);

  UserDetailDto toUserDetailDto(UserDetail userDetail);

  PlatformUserCertificationDto toPlatformUserCertificationDto(PlatformUserCertification platformUserCertification);

  List<PlatformUserCertificationDto> toPlatformUserCertificationDto(
      List<PlatformUserCertification> platformUserCertification);

  List<PlatformUserLanguageDto> toPlatformUserLanguageDto(List<PlatformUserLanguage> platformUserLanguages);

  PlatformUserLanguageDto toPlatformUserLanguageDto(PlatformUserLanguage platformUserLanguage);

  List<PlatformUserEducationDto> toPlatformUserEducationDto(List<PlatformUserEducation> platformUserEducations);

  PlatformUserEducationDto toPlatformUserEducationDto(PlatformUserEducation platformUserEducation);
}
