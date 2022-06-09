package com.sparqr.cx.iam.api.dtos.inbound;

import com.sparqr.cx.iam.api.commons.GenderEnum;
import java.time.LocalDate;
import javax.validation.constraints.Email;
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
public class UpdatePlatformUserDto {

  @Size(max = 50)
  private String alias;

  @NotBlank
  @Size(max = 50)
  private String externalRef;

  @NotBlank
  @Size(max = 255)
  @Email
  private String email;

  @Size(max = 50)
  private String identityProvider;

  @Size(max = 255)
  private String firstName;

  @Size(max = 255)
  private String lastName;

  @Size(max = 1)
  private GenderEnum gender;

  @Size(max = 50)
  private String contactNo;

  private LocalDate dob;

  @Size(max = 2)
  private String countryCode;

}