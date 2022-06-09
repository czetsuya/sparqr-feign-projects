package com.sparqr.cx.iam.services.pojos;

import com.sparqr.cx.iam.api.commons.LanguageLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUserLanguage {

  private Long id;
  private String languageCode;
  private LanguageLevelEnum languageLevel;
}
