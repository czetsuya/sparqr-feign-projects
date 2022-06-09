package com.sparqr.cx.be.common.web.advices;

import com.sparqr.cx.be.iam.ErrorTypes;
import java.util.Set;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;

public class Problems {

  private static final String ERROR_CODE = "errorCode";
  private static final String ERROR_METADATA = "errorMetadata";

  public static Problem feignException(int status, String errorCode, String detail) {

    return getProblemBuilder(Status.valueOf(status), detail, errorCode)
        .build();
  }

  public static Problem missingRequiredFields(Set<String> fields) {

    String detail = ErrorTypes.BE_100.getMessage();
    return getProblemBuilder(Status.BAD_REQUEST, detail, ErrorTypes.BE_100.getErrorId())
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
