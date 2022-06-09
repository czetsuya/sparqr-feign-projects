package com.sparqr.cx.booking.persistence.entities.base;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auditable implements Serializable {

  private static final long serialVersionUID = 6534747210502098594L;

  @CreatedDate
  @Column(name = "created", nullable = false, updatable = false)
  private LocalDateTime created;

  @LastModifiedDate
  @Column(name = "updated")
  private LocalDateTime updated;

  @CreatedBy
  @Column(name = "creator_ref")
  private String creatorRef;

  @LastModifiedBy
  @Column(name = "updater_ref")
  private String updaterRef;

  public Auditable(String userRef) {
    super();
    this.creatorRef = userRef;
    this.created = LocalDateTime.now();
  }

  public void updateWith(String userRef) {
    this.updated = LocalDateTime.now();
    this.updaterRef = userRef;

    if (this.creatorRef == null) {
      this.creatorRef = userRef;
    }

    if (this.created == null) {
      this.created = this.updated;
    }
  }

}
