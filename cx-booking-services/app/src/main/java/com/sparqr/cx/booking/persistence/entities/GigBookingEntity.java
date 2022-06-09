package com.sparqr.cx.booking.persistence.entities;

import com.sparqr.cx.booking.api.commons.GigBookingStatusEnum;
import com.sparqr.cx.booking.persistence.entities.base.BaseEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
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
@Table(name = "gig_booking")
@EqualsAndHashCode(callSuper = true)
public class GigBookingEntity extends BaseEntity {

  @Column(name = "booker_id", nullable = false, updatable = false)
  private Long bookerId;

  @Column(name = "package_id", nullable = false, updatable = false)
  private Long gigPackageId;

  @Column(name = "start_date", nullable = false, updatable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false, updatable = false)
  private LocalDate endDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 25)
  private GigBookingStatusEnum status;
}
