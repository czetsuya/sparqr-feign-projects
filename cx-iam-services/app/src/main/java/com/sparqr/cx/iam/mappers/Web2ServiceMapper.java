package com.sparqr.cx.iam.mappers;

import com.sparqr.cx.iam.api.dtos.commons.PlatformUserCertificationDto;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserEducationDto;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserLanguageDto;
import com.sparqr.cx.iam.api.dtos.inbound.CreateOrUpdateUserProfileRequestDto;
import com.sparqr.cx.iam.api.dtos.inbound.MapUpdateOrCreateIfAbsentRequest;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateUserDetailDto;
import com.sparqr.cx.iam.services.pojos.PlatformUser;
import com.sparqr.cx.iam.services.pojos.PlatformUserCertification;
import com.sparqr.cx.iam.services.pojos.PlatformUserEducation;
import com.sparqr.cx.iam.services.pojos.PlatformUserLanguage;
import com.sparqr.cx.iam.services.pojos.PlatformUserProfile;
import com.sparqr.cx.iam.services.pojos.UserDetail;
import java.time.LocalDateTime;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {
    LocalDateTime.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface Web2ServiceMapper extends LocalDateMapper {

  UserDetail toUserDetail(UpdateUserDetailDto updateUserDetailDto);

  @Mapping(target = "created", defaultExpression = "java(LocalDateTime.now())")
  PlatformUser toUserDetail(MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest);

  PlatformUserProfile toPlatformUserProfile(CreateOrUpdateUserProfileRequestDto createOrUpdateUserProfileRequestDto);

  @Mapping(target = "created", defaultExpression = "java(LocalDateTime.now())")
  PlatformUser toPlatformUser(MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest);

  PlatformUserCertification toPlatformUserCertification(PlatformUserCertificationDto platformUserCertificationDto);

  PlatformUserLanguage toPlatformUserLanguage(PlatformUserLanguageDto platformUserLanguageDto);

  PlatformUserEducation toPlatformUserEducation(PlatformUserEducationDto platformUserEducationDto);
}
