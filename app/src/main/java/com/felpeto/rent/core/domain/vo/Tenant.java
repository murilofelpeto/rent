package com.felpeto.rent.core.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public class Tenant {

  private static final String MANDATORY_FIELD = "Tenant | value is mandatory";
  private final String value;

  private Tenant(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new RuntimeException(MANDATORY_FIELD);
    }

    this.value = value;
  }

  public static Tenant of(final String tenant) {
    return new Tenant(tenant);
  }
}
