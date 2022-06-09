package com.sparqr.cx.iam.services.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {

  private PlatformUser user;
  private PlatformUserProfile profile;
}
