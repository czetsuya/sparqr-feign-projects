package com.sparqr.cx.gig.persistence.entities;

import com.sparqr.cx.gig.api.commons.GigPackageTypeEnum;
import com.sparqr.cx.gig.persistence.converters.StringListConverter;
import com.sparqr.cx.gig.persistence.entities.base.BaseEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gig_package")
@EqualsAndHashCode(callSuper = true)
public class GigPackageEntity extends BaseEntity {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "gig_id", nullable = false)
  private GigEntity gig;

  @Enumerated(EnumType.STRING)
  @Column(name = "package_type", length = 25)
  private GigPackageTypeEnum packageType;

  @Column(name = "description", length = 512)
  private String description;

  @Column(name = "cost", precision = NB_PRECISION, scale = NB_SCALE)
  private BigDecimal cost;

  @Column(name = "no_of_revisions")
  private Integer noOfRevisions;

  @Column(name = "no_of_days")
  private Integer noOfDays;

  @Column(name = "deliverables", columnDefinition = "TEXT")
  @Convert(converter = StringListConverter.class)
  private List<String> deliverables;
}
