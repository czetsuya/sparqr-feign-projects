package com.sparqr.cx.be.common.web.advices;

import static org.zalando.problem.Status.BAD_REQUEST;
import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparqr.cx.be.common.services.exceptions.MissingRequiredFieldsException;
import feign.FeignException;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.common.MediaTypes;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ExceptionControllerAdvice implements ProblemHandling {

  private static final String IS_NOT_VALID = " is not valid ";

  private final ObjectMapper objectMapper;

  @ExceptionHandler(FeignException.class)
  public ResponseEntity<Problem> handleFeignStatusException(FeignException e) {

    log.error("Error: " + e.getMessage(), e);

    try {
      Problem problem = objectMapper.readValue(e.contentUTF8(), Problem.class);

      return ResponseEntity.status(e.status())
          .contentType(MediaTypes.PROBLEM)
          .body(problem);

    } catch (JsonProcessingException jpe) {
      logError(e, "");

      Problem problem = Problem
          .valueOf(INTERNAL_SERVER_ERROR, e.getMessage());
      return ResponseEntity.status(e.status())
          .contentType(MediaTypes.PROBLEM)
          .body(problem);
    }
  }

  /**
   * Handler used for managing MissingRequiredFieldsException in controller.
   *
   * @param exception - {@link MissingRequiredFieldsException}
   * @return - Problem BE-100
   */
  @ExceptionHandler(MissingRequiredFieldsException.class)
  public ResponseEntity<Problem> handleException(MissingRequiredFieldsException exception) {

    log.error("Error: " + exception.getMessage(), exception);

    return ResponseEntity.status(Status.BAD_REQUEST.getStatusCode())
        .contentType(MediaTypes.PROBLEM)
        .body(Problems.missingRequiredFields(exception.getFields()));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Problem> handleException(
      MethodArgumentTypeMismatchException e) {

    String requiredType = Optional.ofNullable(e.getRequiredType())
        .map(Class::getSimpleName)
        .orElse("");

    String message = e.getName() + IS_NOT_VALID + requiredType;

    log.error("Error: " + message, e);

    Problem problem = Problem.valueOf(Status.BAD_REQUEST, message);

    return ResponseEntity.status(Status.BAD_REQUEST.getStatusCode())
        .contentType(MediaTypes.PROBLEM)
        .body(problem);
  }


  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Problem> handleException(IllegalArgumentException exception) {

    String message = exception.getMessage();

    logError(exception, message);

    Problem problem = Problem.valueOf(BAD_REQUEST, message);

    return ResponseEntity.status(Objects.requireNonNull(problem.getStatus()).getStatusCode())
        .contentType(MediaTypes.PROBLEM)
        .body(problem);
  }


  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Problem> handleException(EntityNotFoundException exception) {
    String message = exception.getMessage();

    logError(exception, message);

    Problem problem = Problem.valueOf(Status.NOT_FOUND, message);

    return ResponseEntity.status(Objects.requireNonNull(problem.getStatus()).getStatusCode())
        .contentType(MediaTypes.PROBLEM)
        .body(problem);
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<Problem> handleException(HttpClientErrorException exception) {

    String message = exception.getMessage();

    if (message == null) {
      message = "Error without message";

    } else {
      if (message.contains("[") && message.contains("]")) {
        message = message
            .substring(message.indexOf('[') + 1, message.lastIndexOf(']'));
      }
    }

    try {
      Problem problem = objectMapper.readValue(message, Problem.class);
      return ResponseEntity.status(exception.getStatusCode())
          .contentType(MediaTypes.PROBLEM)
          .body(problem);

    } catch (JsonProcessingException e) {
      logError(e, "");

      Problem problem = Problem
          .valueOf(INTERNAL_SERVER_ERROR, exception.getResponseBodyAsString());
      return ResponseEntity.status(exception.getStatusCode())
          .contentType(MediaTypes.PROBLEM)
          .body(problem);
    }
  }

  private void logError(Exception exception, String message) {
    log.error("Error: " + message, exception);
  }
}
