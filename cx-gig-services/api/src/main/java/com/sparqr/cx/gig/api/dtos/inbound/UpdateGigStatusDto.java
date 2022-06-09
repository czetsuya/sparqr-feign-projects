package com.sparqr.cx.gig.api.dtos.inbound;

import com.sparqr.cx.gig.api.commons.GigStatusEnum;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGigStatusDto {

  @NotNull
  private GigStatusEnum status;
}
