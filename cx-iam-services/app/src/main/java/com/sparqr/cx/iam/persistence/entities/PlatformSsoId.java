package com.sparqr.cx.iam.persistence.entities;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformSsoId implements Serializable {

  private String externalRef;
  private String identityProvider;
}
