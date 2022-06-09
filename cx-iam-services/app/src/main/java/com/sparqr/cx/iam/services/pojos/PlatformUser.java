package com.sparqr.cx.iam.services.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUser {

  private Long id;
  private String alias;
  private String externalRef;
  private String email;
  private String identityProvider;
  private String firstName;
  private String lastName;
  private String gender;
  private String contactNo;
  private LocalDate dob;
  private String countryCode;
  private LocalDateTime created;
}
