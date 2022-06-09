package com.sparqr.cx.booking.services.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

  private String alias;
  private Long id;

  public UserNotFoundException(String alias) {
    this.alias = alias;
  }

  public UserNotFoundException(Long platformUserEntityId) {
    this.id = id;
  }
}
