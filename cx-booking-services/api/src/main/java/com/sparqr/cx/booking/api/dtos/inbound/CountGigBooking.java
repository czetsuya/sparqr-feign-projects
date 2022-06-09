package com.sparqr.cx.booking.api.dtos.inbound;

import com.sparqr.cx.booking.api.commons.GigBookingStatusEnum;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountGigBooking {

  private Set<GigBookingStatusEnum> statuses;
}
