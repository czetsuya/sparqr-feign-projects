package com.sparqr.cx.iam.persistence.entities;

import com.sparqr.cx.iam.persistence.entities.base.AuditableEntity;
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
@Table(name = "platform_user_profile")
@EqualsAndHashCode(callSuper = true)
public class PlatformUserProfileEntity extends AuditableEntity {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "platform_user_id", nullable = false)
  private PlatformUserEntity platformUser;

  @Column(name = "profession", length = 255)
  private String profession;

  @Column(name = "about", length = 100)
  private String about;

  @Column(name = "introduction", columnDefinition = "TEXT")
  private String introduction;

  @Column(name = "profile_picture")
  private String profilePicture;

  @Column(name = "website", length = 512)
  private String website;

  /**
   * Availability to collaborate on a project.
   */
  @Column(name = "availability", length = 50)
  private String availability;
}
