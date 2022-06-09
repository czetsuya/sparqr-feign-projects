package com.sparqr.cx.gig.services.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpsertGig {

  private String title;
  private String detail;
  private Integer maxBooking;
}
