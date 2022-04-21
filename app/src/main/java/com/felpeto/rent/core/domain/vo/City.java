package com.felpeto.rent.core.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public class City {

  private static final String MANDATORY_FIELD = "City | value is mandatory";
  private final String value;

  private City(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new RuntimeException(MANDATORY_FIELD);
    }

    this.value = value;
  }

  public static City of(final String city) {
    return new City(city);
  }
}
