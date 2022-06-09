package com.sparqr.cx.booking.api.dtos.outbound;

import com.sparqr.cx.booking.api.commons.GigBookingStatusEnum;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GigBookingDto {

  private Long bookerId;
  private Long gigPackageId;
  private LocalDate startDate;
  private LocalDate endDate;
  private GigBookingStatusEnum status;
}
