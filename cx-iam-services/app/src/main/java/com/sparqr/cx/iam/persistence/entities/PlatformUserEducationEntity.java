package com.sparqr.cx.iam.persistence.entities;

import com.sparqr.cx.iam.persistence.entities.base.BaseEntity;
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
@Table(name = "platform_user_education")
@EqualsAndHashCode(callSuper = true)
public class PlatformUserEducationEntity extends BaseEntity {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "platform_user_id", nullable = false)
  private PlatformUserEntity platformUser;

  @Column(name = "degree", nullable = false)
  private String degree;

  @Column(name = "school", nullable = false)
  private String school;

  @Column(name = "year_of_graduation")
  private Integer yearOfGraduation;
}
