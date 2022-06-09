package com.sparqr.cx.gig.api.dtos.outbound;

import com.sparqr.cx.gig.api.commons.GigStatusEnum;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GigDto {

  private Long id;

  @NotEmpty
  @Size(max = 100)
  private String title;

  private String detail;

  @NotNull
  private GigStatusEnum status;

  @Min(1)
  private Integer maxBooking;
}
