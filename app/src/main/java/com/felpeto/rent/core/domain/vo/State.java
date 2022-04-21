package com.felpeto.rent.core.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public class State {

  private static final String MANDATORY_FIELD = "State | value is mandatory";
  private final String value;

  private State(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new RuntimeException(MANDATORY_FIELD);
    }

    this.value = value;
  }

  public static State of(final String state) {
    return new State(state);
  }

}
