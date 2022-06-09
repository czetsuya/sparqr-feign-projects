package com.sparqr.cx.iam.persistence.entities;

import com.sparqr.cx.iam.api.commons.LanguageLevelEnum;
import com.sparqr.cx.iam.persistence.entities.base.BaseEntity;
import javax.persistence.Column;
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
@Table(name = "platform_user_language")
@EqualsAndHashCode(callSuper = true)
public class PlatformUserLanguageEntity extends BaseEntity {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "platform_user_id", nullable = false)
  private PlatformUserEntity platformUser;

  @Column(name = "language_code", nullable = false, length = 2, updatable = false)
  private String languageCode;

  @Enumerated(EnumType.STRING)
  @Column(name = "language_level")
  private LanguageLevelEnum languageLevel;
}
