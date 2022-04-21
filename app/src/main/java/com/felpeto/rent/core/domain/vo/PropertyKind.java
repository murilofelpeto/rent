package com.felpeto.rent.core.domain.vo;

import java.util.Arrays;
import java.util.List;

public enum PropertyKind {
  RESIDENTIAL(""),
  COMMERCIAL(""),
  LAND(""),
  INDUSTRIAL("");

  private final List<String> properties;

  PropertyKind(final String... properties) {
    this.properties = List.of(properties);
  }

  public static PropertyKind of (final String propertyKind) {
    return Arrays.stream(values())
        .filter(kind -> kind.properties.contains(propertyKind))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException());
  }
}
