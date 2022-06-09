package com.sparqr.cx.gig.web.advices;

import com.sparqr.cx.gig.api.ErrorTypes;
import java.util.Set;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;

public class Problems {

  private static final String ERROR_CODE = "errorCode";
  private static final String ERROR_METADATA = "errorMetadata";

  public static Problem gigNotActive(Long id) {

    String detail = ErrorTypes.GIG_002.getTemplateMessage(id);
    return getProblemBuilder(Status.BAD_REQUEST, detail, ErrorTypes.GIG_002.getErrorId()).build();
  }

  public static Problem gigNotFound(Long id) {

    String detail = ErrorTypes.GIG_001.getTemplateMessage(id);
    return getProblemBuilder(Status.BAD_REQUEST, detail, ErrorTypes.GIG_001.getErrorId()).build();
  }

  public static Problem missingRequiredFields(Set<String> fields) {

    String detail = ErrorTypes.GIG_100.getMessage();
    return getProblemBuilder(Status.BAD_REQUEST, detail, ErrorTypes.GIG_100.getErrorId())
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
