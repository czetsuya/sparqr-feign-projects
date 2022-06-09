package com.sparqr.cx.booking.services.exceptions;

import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MissingRequiredFieldsException extends RuntimeException {

  private Set<String> fields;

  public MissingRequiredFieldsException(Set<String> fields) {

    super("Missing required fields " + fields);
    this.fields = fields;
  }
}
