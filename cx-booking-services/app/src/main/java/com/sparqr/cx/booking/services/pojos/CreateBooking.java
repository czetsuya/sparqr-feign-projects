package com.sparqr.cx.booking.services.pojos;

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
public class CreateBooking {

  private Long bookerId;
  private Long gigPackageId;
  private LocalDate startDate;
  private LocalDate endDate;
}
