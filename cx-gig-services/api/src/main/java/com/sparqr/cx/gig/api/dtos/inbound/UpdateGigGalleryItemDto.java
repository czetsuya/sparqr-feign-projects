package com.sparqr.cx.gig.api.dtos.inbound;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGigGalleryItemDto {

  @Size(max = 100)
  private String description;

  @Positive
  private Integer sortOrder;
}
