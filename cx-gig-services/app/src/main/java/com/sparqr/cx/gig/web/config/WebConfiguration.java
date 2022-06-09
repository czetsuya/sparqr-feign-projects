package com.sparqr.cx.gig.web.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

  @Bean
  public ProblemModule problemModule() {
    return new ProblemModule();
  }

  @Bean
  public ConstraintViolationProblemModule constraintViolationProblemModule() {
    return new ConstraintViolationProblemModule();
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {

    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE");
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
    stringConverter.setWriteAcceptCharset(false);
    converters.add(0, stringConverter);
  }

  @Bean
  public Filter customResponseHeaderFilter() {

    return (request, response, chain) -> {
      if (Arrays.asList(Locale.getAvailableLocales()).contains(request.getLocale())) {
        response.setLocale(request.getLocale());
      }
      chain.doFilter(request, response);
    };
  }

  @Bean
  public CommonsRequestLoggingFilter logFilter() {

    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(10000);
    filter.setIncludeHeaders(true);
    filter.setAfterMessagePrefix("REQUEST DATA : ");

    return filter;
  }
}
