package com.sparqr.cx.iam.fixtures;

import com.sparqr.cx.iam.api.commons.LanguageLevelEnum;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserCertificationDto;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserEducationDto;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserLanguageDto;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public final class PlatformUserProfileFixtures {

  public static List<PlatformUserCertificationDto> certifications() {
    return List.of(
        PlatformUserCertificationDto.builder()
            .name("Solution Architect")
            .expirationDate(LocalDate.now().plusDays(10L))
            .issuedDate(LocalDate.now())
            .credentialId("C1")
            .issuingOrganization("AWS")
            .build(),
        PlatformUserCertificationDto.builder()
            .name("CSPL")
            .issuedDate(LocalDate.of(2013, Month.OCTOBER, 31))
            .credentialId("C2")
            .issuingOrganization("PCSC")
            .build()
    );
  }

  public static List<PlatformUserEducationDto> educations() {
    return List.of(
        PlatformUserEducationDto.builder()
            .degree("BS Computer Science")
            .school("UPLB")
            .yearOfGraduation(2006)
            .build()
    );
  }

  public static List<PlatformUserLanguageDto> languages() {
    return List.of(
        PlatformUserLanguageDto.builder()
            .languageCode("EN")
            .languageLevel(LanguageLevelEnum.FULL_PROFESSIONAL)
            .build(),
        PlatformUserLanguageDto.builder()
            .languageCode("JA")
            .languageLevel(LanguageLevelEnum.ELEMENTARY)
            .build(),
        PlatformUserLanguageDto.builder()
            .languageCode("TL")
            .languageLevel(LanguageLevelEnum.NATIVE)
            .build()
    );
  }

  public static List<String> skills() {
    return List.of("Java", "AWS", "Microservices", "Spring", "OOP");
  }
}
