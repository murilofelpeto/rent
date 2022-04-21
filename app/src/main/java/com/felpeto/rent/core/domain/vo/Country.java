package com.felpeto.rent.core.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public class Country {

  private static final String MANDATORY_FIELD = "Country | value is mandatory";
  private final String value;

  private Country(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new RuntimeException(MANDATORY_FIELD);
    }

    this.value = value;
  }

  public static Country of(final String country) {
    return new Country(country);
  }
}
