package com.sparqr.cx.gig.services.pojos;

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
public class UpdateGigStatus {

  @NotNull
  private GigStatusEnum status;
}
