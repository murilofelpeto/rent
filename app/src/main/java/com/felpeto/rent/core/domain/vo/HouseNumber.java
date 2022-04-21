package com.felpeto.rent.core.domain.vo;

import static java.util.Objects.requireNonNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class HouseNumber {

  private static final String MANDATORY_FIELD = "HouseNumber | value is mandatory";
  private final Integer value;

  private HouseNumber(final Integer value) {
    this.value = requireNonNull(value, MANDATORY_FIELD);
  }

  public static HouseNumber of(final Integer houseNumber) {
    return new HouseNumber(houseNumber);
  }
}
