package com.sparqr.cx.gig.persistence.converters;

import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

  private final String DELIMITER = ",";

  @Override
  public String convertToDatabaseColumn(List<String> list) {
    return String.join(DELIMITER, list);
  }

  @Override
  public List<String> convertToEntityAttribute(String joined) {
    return Arrays.asList(joined.split(DELIMITER));
  }
}