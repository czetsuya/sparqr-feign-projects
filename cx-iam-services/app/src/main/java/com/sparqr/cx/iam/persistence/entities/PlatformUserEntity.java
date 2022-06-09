package com.sparqr.cx.iam.persistence.entities;

import com.sparqr.cx.iam.api.commons.GenderEnum;
import com.sparqr.cx.iam.persistence.entities.base.EnableEntity;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
@Table(name = "platform_user", indexes = {
    @Index(name = "idx_alias", columnList = "alias"),
    @Index(name = "idx_email", columnList = "email")
})
@EqualsAndHashCode(callSuper = true)
public class PlatformUserEntity extends EnableEntity {

  @NotEmpty
  @NotNull
  @Column(name = "email", length = 255, nullable = false)
  private String email;

  @NotEmpty
  @NotNull
  @Column(name = "alias", length = 100, nullable = false)
  private String alias;

  @Column(name = "first_name", length = 50)
  private String firstName;

  @Column(name = "last_name", length = 50)
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private GenderEnum gender;

  @Column(name = "contact_no")
  private String contactNo;

  @Column(name = "dob")
  private LocalDate dob;

  @ElementCollection
  @CollectionTable(name = "platform_user_skill", joinColumns = @JoinColumn(name = "platform_user_id"))
  @Column(name = "skill", length = 100)
  private Set<String> skills;
}
