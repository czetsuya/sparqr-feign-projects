package com.sparqr.cx.booking.api.dtos.inbound;

import com.sparqr.cx.booking.api.commons.GigBookingStatusEnum;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingDto {

  @NotNull
  private Long bookerId;

  @NotNull
  private Long gigPackageId;

  @NotNull
  private LocalDate startDate;

  @NotNull
  private LocalDate endDate;
}
