package com.sparqr.cx.gig.services.pojos;

import com.sparqr.cx.gig.api.commons.GigStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gig {

  private Long id;
  private String title;
  private String detail;
  private Integer maxBooking;
  private GigStatusEnum status;
}
