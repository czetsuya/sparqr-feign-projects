package com.sparqr.cx.be.iam;

public enum ErrorTypes {

  BE_100("BE-100", "Missing required fields.");

  private final String errorId;
  private final String message;

  ErrorTypes(String errorId, String message) {
    this.errorId = errorId;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getTemplateMessage(Object... args) {
    return String.format(message, args);
  }

  public String getErrorId() {
    return errorId;
  }
}
