package com.sparqr.cx.booking.persistence.entities.base;

import java.io.Serial;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public abstract class EnableEntity extends AuditableEntity {

  @Serial
  private static final long serialVersionUID = -7084847683632507391L;

  private boolean disabled;
}
