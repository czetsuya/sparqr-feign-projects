package com.sparqr.cx.iam.persistence.entities;

import com.sparqr.cx.iam.persistence.entities.base.BaseEntity;
import java.time.LocalDate;
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
@Table(name = "platform_user_certification")
@EqualsAndHashCode(callSuper = true)
public class PlatformUserCertificationEntity extends BaseEntity {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "platform_user_id", nullable = false)
  private PlatformUserEntity platformUser;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "issuing_organization", nullable = false)
  private String issuingOrganization;

  @Column(name = "credential_id", nullable = false)
  private String credentialId;

  @Column(name = "issued_date", nullable = false)
  private LocalDate issuedDate;

  @Column(name = "expiration_date")
  private LocalDate expirationDate;
}
