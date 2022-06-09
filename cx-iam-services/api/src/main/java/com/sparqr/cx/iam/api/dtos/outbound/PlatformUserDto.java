package com.sparqr.cx.iam.api.dtos.outbound;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUserDto {

  private Long id;
  private String alias;
  private String externalRef;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;
  private String contactNo;
  private LocalDate dob;
  private String countryCode;
}
