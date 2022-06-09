package com.sparqr.cx.gig.web.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {

  @Value("${common.date-format}")
  private String dateFormat;

  @Value("${common.datetime-format}")
  private String dateTimeFormat;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(clientHttpRequestFactory());
  }

  private ClientHttpRequestFactory clientHttpRequestFactory() {

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
        HttpClientBuilder.create().useSystemProperties().build());
    factory.setReadTimeout(10000);
    factory.setConnectTimeout(2000);
    factory.setConnectionRequestTimeout(2000);
    return factory;
  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {

    return builder -> builder.simpleDateFormat(dateFormat)
        .serializers(new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
        .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        .deserializers(new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE))
        .deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        .indentOutput(true)
        .serializationInclusion(Include.NON_NULL)
        .failOnUnknownProperties(false);
  }
}
