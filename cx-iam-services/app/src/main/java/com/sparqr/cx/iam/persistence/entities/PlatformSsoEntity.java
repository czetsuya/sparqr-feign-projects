package com.sparqr.cx.iam.persistence.entities;

import com.sparqr.cx.iam.persistence.entities.base.IEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "platform_sso", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"external_ref", "identity_provider"})})
@IdClass(PlatformSsoId.class)
public class PlatformSsoEntity implements Serializable, IEntity {

  @Id
  @NotNull
  @NotEmpty
  @Column(name = "external_ref", nullable = false, length = 50)
  private String externalRef;

  @Id
  @NotNull
  @NotEmpty
  @Column(name = "identity_provider", nullable = false, length = 50)
  private String identityProvider;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "platform_user_id", nullable = false)
  private PlatformUserEntity platformUser;

  @Column(name = "created")
  private LocalDateTime created;

  @Override
  public Serializable getId() {
    return externalRef;
  }

  @Override
  public boolean isTransient() {
    return false;
  }
}
