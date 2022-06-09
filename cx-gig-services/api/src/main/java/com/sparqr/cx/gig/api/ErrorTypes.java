package com.sparqr.cx.gig.api;

public enum ErrorTypes {

  GIG_001("GIG-001", "Gig with id=%d does not exists."),
  GIG_002("GIG-002", "Gig with id=%d is not active."),
  GIG_100("GIG-100", "Missing required fields.");

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
