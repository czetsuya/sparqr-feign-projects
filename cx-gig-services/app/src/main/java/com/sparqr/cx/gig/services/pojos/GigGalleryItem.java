package com.sparqr.cx.gig.services.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GigGalleryItem {

  private Long id;
  private String imageUrl;
  private String description;
  private Integer sortOrder;
}
