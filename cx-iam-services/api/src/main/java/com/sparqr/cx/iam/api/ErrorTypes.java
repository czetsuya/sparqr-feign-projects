package com.sparqr.cx.iam.api;

public enum ErrorTypes {

  IAM_001("IAM-001", "User with id=%d does not exists."),
  IAM_002("IAM-002", "User with alias=%s does not exists."),
  IAM_003("IAM-100", "Missing required fields.");

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
