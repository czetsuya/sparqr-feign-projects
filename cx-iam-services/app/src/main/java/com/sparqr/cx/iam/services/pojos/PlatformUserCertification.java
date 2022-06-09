package com.sparqr.cx.iam.services.pojos;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUserCertification {

  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String issuingOrganization;

  @NotBlank
  private String credentialId;

  @NotNull
  private LocalDate issuedDate;

  private LocalDate expirationDate;
}
