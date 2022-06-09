package com.sparqr.cx.iam.mappers;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import org.mapstruct.Named;

public interface LocalDateMapper {

  @Named("stringToLocalDateTime")
  default LocalDateTime stringToLocalDateTime(final String value) {
    DateTimeFormatter dateTimeFormatter =
        new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .toFormatter();

    return LocalDateTime.parse(value, dateTimeFormatter);
  }
}
