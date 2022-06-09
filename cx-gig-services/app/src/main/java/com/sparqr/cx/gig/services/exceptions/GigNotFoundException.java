package com.sparqr.cx.gig.services.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GigNotFoundException extends RuntimeException {

  private Long id;

  public GigNotFoundException(Long id) {
    this.id = id;
  }
}
