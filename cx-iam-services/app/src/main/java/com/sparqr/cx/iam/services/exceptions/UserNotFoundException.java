package com.sparqr.cx.iam.services.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserNotFoundException extends RuntimeException {

  private String alias;
  private Long id;

  public UserNotFoundException(String alias) {
    this.alias = alias;
  }

  public UserNotFoundException(Long id) {
    this.id = id;
  }
}
