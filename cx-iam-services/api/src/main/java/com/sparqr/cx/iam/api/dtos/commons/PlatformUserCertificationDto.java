package com.sparqr.cx.iam.api.dtos.commons;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUserCertificationDto {

  
  Long id;

  @NotBlank
  @Size(max = 100)
  private String name;

  @NotBlank
  @Size(max = 100)
  private String issuingOrganization;

  @NotBlank
  @Size(max = 100)
  private String credentialId;

  @NotNull
  private LocalDate issuedDate;

  private LocalDate expirationDate;
}
