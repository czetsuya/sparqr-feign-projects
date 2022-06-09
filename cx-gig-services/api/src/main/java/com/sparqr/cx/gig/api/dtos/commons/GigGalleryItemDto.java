package com.sparqr.cx.gig.api.dtos.commons;

import javax.validation.constraints.NotEmpty;
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
public class GigGalleryItemDto {

  private Long id;

  @NotEmpty
  @Size(max = 100)
  private String imageUrl;

  @Size(max = 100)
  private String description;

  @Positive
  private Integer sortOrder;
}
