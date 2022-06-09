package com.sparqr.cx.iam.api.dtos.inbound;

import com.sparqr.cx.iam.api.EndpointConstants;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAliasDto {

  @NotEmpty
  @Size(max = 50)
  @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS)
  private String alias;
}
