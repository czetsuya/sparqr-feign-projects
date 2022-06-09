package com.sparqr.cx.iam.api.dtos.commons;

import com.sparqr.cx.iam.api.commons.LanguageLevelEnum;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUserLanguageDto {

  
  private Long id;

  @NotBlank
  @Size(max = 2)
  private String languageCode;

  private LanguageLevelEnum languageLevel;
}
