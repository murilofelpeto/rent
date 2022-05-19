package com.felpeto.rent.adapter.output.mysql.mapper;

import static com.felpeto.rent.adapter.output.mysql.mapper.PropertyEntityMapper.toEntity;
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

class PropertyEntityMapperTest {

  private final Faker faker = new Faker();

  @Test
  void givenPropertyWithoutUuidWhenMapThenReturnPropertyEntity() {
    final var property = buildPropertyWithoutUuid();
    final var entity = toEntity(property);

    final var address = property.getAddress();

    assertThat(entity).isNotNull();
    assertThat(entity.getUuid()).isNull();
    assertThat(entity.getPropertyKind()).isEqualTo(property.getPropertyKind());
    assertThat(entity.getTenant()).isEqualTo(property.getTenant().getValue());
    assertThat(entity.getCity()).isEqualTo(address.getCity().getValue());
    assertThat(entity.getComplement()).isEqualTo(address.getComplement());
    assertThat(entity.getCountry()).isEqualTo(address.getCountry().getValue());
    assertThat(entity.getState()).isEqualTo(address.getState().getValue());
    assertThat(entity.getStreetName()).isEqualTo(address.getStreetName().getValue());
    assertThat(entity.getHouseNumber()).isEqualTo(address.getNumber().getValue());
    assertThat(entity.getZipcode()).isEqualTo(address.getZipCode().getValue());
    assertThat(entity.getId()).isNull();
  }

  @Test
  void givenPropertyWithUuidWhenMapThenReturnPropertyEntity() {
    final var property = buildPropertyWithUuid();
    final var entity = toEntity(property);

    assertThat(entity).isNotNull();
    assertThat(entity.getUuid()).isEqualTo(property.getUuid());
  }

  private Property buildPropertyWithoutUuid() {
    final var address = buildAddress();
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var tenant = Tenant.of(faker.name().username());

    return Property.withoutUuid()
        .propertyKind(propertyKind)
        .address(address)
        .tenant(tenant)
        .create();
  }

  private Property buildPropertyWithUuid() {
    final var address = buildAddress();
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var tenant = Tenant.of(faker.name().username());
    final var uuid = UUID.randomUUID();

    return Property.builder()
        .propertyKind(propertyKind)
        .address(address)
        .tenant(tenant)
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