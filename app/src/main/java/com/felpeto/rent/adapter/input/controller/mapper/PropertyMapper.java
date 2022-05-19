package com.felpeto.rent.adapter.input.controller.mapper;

import com.felpeto.rent.adapter.input.controller.dto.request.PropertyRequestDto;
import com.felpeto.rent.core.domain.Address;
import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.domain.vo.City;
import com.felpeto.rent.core.domain.vo.Country;
import com.felpeto.rent.core.domain.vo.HouseNumber;
import com.felpeto.rent.core.domain.vo.PropertyKind;
import com.felpeto.rent.core.domain.vo.State;
import com.felpeto.rent.core.domain.vo.StreetName;
import com.felpeto.rent.core.domain.vo.Tenant;
import com.felpeto.rent.core.domain.vo.ZipCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyMapper {

  public static Property toProperty(final PropertyRequestDto dto) {
    return Property.withoutUuid()
        .propertyKind(PropertyKind.of(dto.getPropertyKind()))
        .tenant(Tenant.of("Teste"))
        .address(toAddress(dto))
        .create();
  }

  private static Address toAddress(final PropertyRequestDto dto) {
    return Address.builder()
        .city(City.of(dto.getCity()))
        .complement(dto.getComplement())
        .country(Country.of(dto.getCountry()))
        .number(HouseNumber.of(dto.getNumber()))
        .state(State.of(dto.getState()))
        .streetName(StreetName.of(dto.getStreetName()))
        .zipCode(ZipCode.of(dto.getZipCode()))
        .build();
  }
}
