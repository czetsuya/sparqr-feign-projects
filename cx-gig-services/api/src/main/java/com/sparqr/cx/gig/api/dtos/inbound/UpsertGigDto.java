package com.sparqr.cx.gig.api.dtos.inbound;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpsertGigDto {

  @NotEmpty
  @Size(max = 100)
  private String title;

  private String detail;

  @Min(1)
  private Integer maxBooking;
}
