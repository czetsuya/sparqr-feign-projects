package com.sparqr.cx.gig.services.pojos;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GigPackage {

  private Long id;
  private String packageType;
  private String description;
  private BigDecimal cost;
  private Integer noOfRevisions;
  private Integer noOfDays;
  private List<String> deliverables;
}
