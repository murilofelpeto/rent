package com.felpeto.rent.core.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public class ZipCode {

  private static final String MANDATORY_FIELD = "ZipCode | value is mandatory";
  private final String value;

  private ZipCode(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new RuntimeException(MANDATORY_FIELD);
    }

    this.value = value;
  }

  public static ZipCode of(final String zipCode) {
    return new ZipCode(zipCode);
  }
}
