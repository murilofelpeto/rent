package com.felpeto.rent.core.domain;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

import com.felpeto.rent.core.domain.vo.PropertyKind;
import com.felpeto.rent.core.domain.vo.Tenant;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@ToString
@EqualsAndHashCode
@FieldNameConstants
public final class Property {

  private static final String MANDATORY_FIELD = "Property | Field {0} is mandatory";
  private final UUID uuid;
  private final PropertyKind propertyKind;
  private final Address address;
  private final Tenant tenant;


  @Builder
  public Property(
      final UUID uuid,
      final PropertyKind propertyKind,
      final Address address,
      final Tenant tenant) {

    this.uuid = requireNonNull(uuid, format(MANDATORY_FIELD, Fields.uuid));
    this.propertyKind = requireNonNull(propertyKind, format(MANDATORY_FIELD, Fields.propertyKind));
    this.address = requireNonNull(address, format(MANDATORY_FIELD, Fields.address));
    this.tenant = requireNonNull(tenant, format(MANDATORY_FIELD, Fields.tenant));
  }

  @Builder(builderMethodName = "withoutUuid", buildMethodName = "create")
  public Property(
      final PropertyKind propertyKind,
      final Address address,
      final Tenant tenant) {

    this.uuid = null;
    this.propertyKind = requireNonNull(propertyKind, format(MANDATORY_FIELD, Fields.propertyKind));
    this.address = requireNonNull(address, format(MANDATORY_FIELD, Fields.address));
    this.tenant = requireNonNull(tenant, format(MANDATORY_FIELD, Fields.tenant));
  }
}
