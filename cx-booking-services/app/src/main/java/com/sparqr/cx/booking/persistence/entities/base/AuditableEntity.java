package com.sparqr.cx.booking.persistence.entities.base;

import java.io.Serial;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@SuperBuilder
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public abstract class AuditableEntity extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 2522072952461930125L;

  @CreatedDate
  @Column(name = "created", nullable = false, updatable = false)
  private LocalDateTime created;

  @LastModifiedDate
  @Column(name = "updated")
  private LocalDateTime updated;

  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @LastModifiedBy
  @Column(name = "updated_by")
  private String updatedBy;
}