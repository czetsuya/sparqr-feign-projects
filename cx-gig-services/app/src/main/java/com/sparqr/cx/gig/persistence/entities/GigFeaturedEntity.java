package com.sparqr.cx.gig.persistence.entities;

import com.sparqr.cx.gig.persistence.entities.base.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "gig_featured")
@EqualsAndHashCode(callSuper = true)
public class GigFeaturedEntity extends BaseEntity {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "gig_id", nullable = false)
  private GigEntity gig;

  @Column(name = "start_date")
  private LocalDateTime startDate;

  @Column(name = "end_date")
  private LocalDateTime endDate;
}
