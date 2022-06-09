package com.sparqr.cx.gig.services.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GigNotActiveException extends RuntimeException {

  private Long id;

  public GigNotActiveException(Long id) {
    this.id = id;
  }
}
