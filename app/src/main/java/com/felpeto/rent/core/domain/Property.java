package com.felpeto.rent.core.domain;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

import com.felpeto.rent.core.domain.vo.PropertyKind;
import java.util.UUID;

public record Property(UUID uuid,
                       PropertyKind propertyKind,
                       Address address) {

  private static final String MANDATORY_FIELD = "Property | Field {0} is mandatory";

  public Property(
      final UUID uuid,
      final PropertyKind propertyKind,
      final Address address) {

    this.uuid = requireNonNull(uuid, format(MANDATORY_FIELD, "uuid"));
    this.propertyKind = requireNonNull(propertyKind, format(MANDATORY_FIELD, "propertyKind"));
    this.address = requireNonNull(address, format(MANDATORY_FIELD, "address"));
  }
}
