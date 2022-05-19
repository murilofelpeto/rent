package com.felpeto.rent.adapter.input.controller.mapper;

import static com.felpeto.rent.adapter.input.controller.mapper.PropertyResponseMapper.toPropertyResponse;
import static org.assertj.core.api.Assertions.assertThat;

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
import com.github.javafaker.Faker;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PropertyResponseMapperTest {

  private final Faker faker = new Faker();

  @Test
  void givenPropertyWhenMapThenReturnPropertyResponse() {
    final var property = buildProperty();
    final var address = property.getAddress();

    final var response = toPropertyResponse(property);

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo(property.getUuid());
    assertThat(response.getPropertyKind()).isEqualTo(property.getPropertyKind().name());

    assertThat(response.getCity()).isEqualTo(address.getCity().getValue());
    assertThat(response.getComplement()).isEqualTo(address.getComplement());
    assertThat(response.getState()).isEqualTo(address.getState().getValue());
    assertThat(response.getNumber()).isEqualTo(address.getNumber().getValue());
    assertThat(response.getCountry()).isEqualTo(address.getCountry().getValue());
    assertThat(response.getZipCode()).isEqualTo(address.getZipCode().getValue());
    assertThat(response.getStreetName()).isEqualTo(address.getStreetName().getValue());

  }

  private Property buildProperty() {
    final var address = buildAddress();
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var uuid = UUID.randomUUID();
    final var tenant = Tenant.of(faker.name().username());
    return Property.builder()
        .address(address)
        .tenant(tenant)
        .propertyKind(propertyKind)
        .uuid(uuid)
        .build();
  }

  private Address buildAddress() {
    final var country = Country.of(faker.address().country());
    final var state = State.of(faker.address().stateAbbr());
    final var city = City.of(faker.address().city());
    final var zipCode = ZipCode.of(faker.address().zipCode());
    final var streetName = StreetName.of(faker.address().streetName());
    final var houseNumber = HouseNumber.of(faker.number().numberBetween(1, 9999));
    final var complement = faker.address().secondaryAddress();

    return new Address(country, state, city, zipCode, streetName, houseNumber, complement);
  }
}