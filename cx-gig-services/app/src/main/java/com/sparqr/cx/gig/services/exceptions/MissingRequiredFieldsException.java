package com.sparqr.cx.gig.services.exceptions;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MissingRequiredFieldsException extends RuntimeException {

  private Set<String> fields;

  public MissingRequiredFieldsException(Set<String> fields) {

    super("Missing required fields " + fields);
    this.fields = fields;
  }
}
