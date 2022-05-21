package com.felpeto.rent.adapter.output.mysql.mapper;

import com.felpeto.rent.adapter.output.mysql.entity.PropertyEntity;
import com.felpeto.rent.core.domain.Address;
import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.domain.vo.City;
import com.felpeto.rent.core.domain.vo.Country;
import com.felpeto.rent.core.domain.vo.HouseNumber;
import com.felpeto.rent.core.domain.vo.State;
import com.felpeto.rent.core.domain.vo.StreetName;
import com.felpeto.rent.core.domain.vo.Tenant;
import com.felpeto.rent.core.domain.vo.ZipCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyMapper {

  public static List<Property> toProperty(final List<PropertyEntity> entities) {
    return entities.stream()
        .map(PropertyMapper::toProperty)
        .collect(Collectors.toList());
  }

  public static Property toProperty(final PropertyEntity entity) {
    return Property.builder()
        .uuid(entity.getUuid())
        .propertyKind(entity.getPropertyKind())
        .tenant(Tenant.of(entity.getTenant()))
        .address(toAddress(entity))
        .build();
  }

  private static Address toAddress(final PropertyEntity entity) {
    return Address.builder()
        .zipCode(ZipCode.of(entity.getZipcode()))
        .streetName(StreetName.of(entity.getStreetName()))
        .state(State.of(entity.getState()))
        .number(HouseNumber.of(entity.getHouseNumber()))
        .country(Country.of(entity.getCountry()))
        .complement(entity.getComplement())
        .city(City.of(entity.getCity()))
        .build();
  }
}
