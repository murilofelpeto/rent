package com.felpeto.rent.adapter.output.mysql.mapper;

import com.felpeto.rent.adapter.output.mysql.entity.PropertyEntity;
import com.felpeto.rent.core.domain.Property;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyEntityMapper {

  public static PropertyEntity toEntity(final Property property) {
    return PropertyEntity.builder()
        .propertyKind(property.getPropertyKind())
        .tenant(property.getTenant().getValue())
        .uuid(property.getUuid() == null ? null : property.getUuid())
        .city(property.getAddress().getCity().getValue())
        .complement(property.getAddress().getComplement())
        .country(property.getAddress().getCountry().getValue())
        .houseNumber(property.getAddress().getNumber().getValue())
        .state(property.getAddress().getState().getValue())
        .streetName(property.getAddress().getStreetName().getValue())
        .zipcode(property.getAddress().getZipCode().getValue())
        .build();
  }

}
