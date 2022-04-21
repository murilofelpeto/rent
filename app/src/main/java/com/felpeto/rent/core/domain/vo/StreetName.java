package com.felpeto.rent.core.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public class StreetName {

  private static final String MANDATORY_FIELD = "StreetName | value is mandatory";
  private final String value;

  private StreetName(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new RuntimeException(MANDATORY_FIELD);
    }

    this.value = value;
  }

  public static StreetName of(final String streetName) {
    return new StreetName(streetName);
  }
}
