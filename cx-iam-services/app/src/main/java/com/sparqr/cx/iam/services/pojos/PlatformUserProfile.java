package com.sparqr.cx.iam.services.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUserProfile {

  private String profession;
  private String about;
  private String website;
  private String availability;
  private String introduction;
  private String profilePicture;
}
