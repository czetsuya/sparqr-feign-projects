package com.sparqr.cx.booking.api;

public enum ErrorTypes {

  BOOK_001("BOOK-001", "Max booking of %d has been reach for gig with id=%d."),
  BOOK_100("BOOK-100", "Missing required fields.");


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
