package com.sparqr.cx.iam.api.dtos.inbound;

import java.time.LocalDateTime;
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
public class MapUpdateOrCreateIfAbsentRequest {

  /**
   * Unique id from the SSO provider.
   */
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

  /**
   * Placeholder for mapping. Don't set.
   */
  private LocalDateTime created;
}
