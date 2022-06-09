package com.sparqr.cx.gig.api.dtos.commons;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GigPackageDto {

  private Long id;

  @NotEmpty
  @Size(max = 50)
  private String packageType;

  @NotEmpty
  @Size(max = 512)
  private String description;

  @NotNull
  @Positive
  private BigDecimal cost;

  @NotNull
  @Positive
  private Integer noOfRevisions;

  @NotNull
  @Positive
  private Integer noOfDays;

  @NotEmpty
  private List<String> deliverables;
}
