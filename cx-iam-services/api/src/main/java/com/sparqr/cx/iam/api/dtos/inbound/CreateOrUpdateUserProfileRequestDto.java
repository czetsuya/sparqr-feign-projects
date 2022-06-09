package com.sparqr.cx.iam.api.dtos.inbound;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateUserProfileRequestDto {

  @Size(max = 255)
  private String profession;

  @Size(max = 100)
  private String about;

  @Size(max = 512)
  private String website;

  @Size(max = 50)
  private String availability;

  private String introduction;

  private String profilePicture;
}