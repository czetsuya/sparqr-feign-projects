package com.sparqr.cx.gig.persistence.entities;

import com.sparqr.cx.gig.api.commons.GigStatusEnum;
import com.sparqr.cx.gig.persistence.entities.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
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
@Table(name = "gig", indexes = {
    @Index(name = "idx_gig_user_id", columnList = "user_id")
})
@EqualsAndHashCode(callSuper = true)
public class GigEntity extends BaseEntity {

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private GigStatusEnum status;

  @Column(name = "title", length = 100)
  private String title;

  @Column(name = "detail", columnDefinition = "TEXT")
  private String detail;

  /**
   * Max no of booking on a given time.
   */
  @Column(name = "max_booking")
  private Integer maxBooking;
}
