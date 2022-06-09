package com.sparqr.cx.gig.web.advices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparqr.cx.gig.web.advices.ExceptionControllerAdvice;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionControllerAdviceTest {

  ObjectMapper objectMapper = new ObjectMapper();

  private final ExceptionControllerAdvice exceptionControllerAdvice =
      new ExceptionControllerAdvice(objectMapper);
}
