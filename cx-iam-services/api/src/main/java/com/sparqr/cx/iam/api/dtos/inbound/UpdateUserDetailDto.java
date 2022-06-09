package com.sparqr.cx.iam.api.dtos.inbound;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDetailDto {

  /**
   * Basic user information.
   */
  private UpdatePlatformUserDto user;

  /**
   * Additional user information.
   */
  private CreateOrUpdateUserProfileRequestDto profile;
}
