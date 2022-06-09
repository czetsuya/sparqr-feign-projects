package com.sparqr.cx.booking.web.advices;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionControllerAdviceTest {

  ObjectMapper objectMapper = new ObjectMapper();

  private final ExceptionControllerAdvice exceptionControllerAdvice =
      new ExceptionControllerAdvice(objectMapper);
}
