package com.felpeto.rent.adapter.input.controller.mapper;

import com.felpeto.rent.adapter.input.controller.dto.response.PropertyResponseDto;
import com.felpeto.rent.core.domain.Property;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyResponseMapper {

  public static PropertyResponseDto toPropertyResponse(final Property property) {
    final var address = property.getAddress();

    return PropertyResponseDto.builder()
        .id(property.getUuid())
        .city(address.getCity().getValue())
        .propertyKind(property.getPropertyKind().name())
        .complement(address.getComplement())
        .country(address.getCountry().getValue())
        .number(address.getNumber().getValue())
        .state(address.getState().getValue())
        .streetName(address.getStreetName().getValue())
        .zipCode(address.getZipCode().getValue())
        .build();
  }
}
