package com.sparqr.cx.iam.api.dtos.commons;

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
public class PlatformUserEducationDto {

  
  private Long id;

  @NotBlank
  @Size(max = 100)
  private String degree;

  @NotBlank
  @Size(max = 255)
  private String school;

  private Integer yearOfGraduation;
}
