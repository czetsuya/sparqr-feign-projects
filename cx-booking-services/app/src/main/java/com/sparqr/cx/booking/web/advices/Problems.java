package com.sparqr.cx.booking.web.advices;

import static com.sparqr.cx.booking.api.ErrorTypes.BOOK_001;
import static com.sparqr.cx.booking.api.ErrorTypes.BOOK_100;

import java.util.Set;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;

public class Problems {

  private static final String ERROR_CODE = "errorCode";
  private static final String ERROR_METADATA = "errorMetadata";

  public static Problem gigMaxBookingReached(Long gigId, Integer maxBooking) {

    String detail = BOOK_001.getTemplateMessage(gigId, maxBooking);
    return getProblemBuilder(Status.BAD_REQUEST, detail,
        BOOK_001.getErrorId()).build();
  }

  public static Problem missingRequiredFields(Set<String> fields) {

    String detail = BOOK_100.getMessage();
    return getProblemBuilder(Status.BAD_REQUEST, detail, BOOK_100.getErrorId())
        .with(ERROR_METADATA, fields)
        .build();
  }

  private static ProblemBuilder getProblemBuilder(Status status, String detail, String errorCode) {

    return Problem.builder()
        .withTitle(status.getReasonPhrase())
        .withStatus(status)
        .withDetail(detail)
        .with(ERROR_CODE, errorCode);
  }


}
