package com.sparqr.cx.iam.api.dtos.outbound;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {

  private PlatformUserDto user;
  private PlatformUserProfileDto profile;
}
