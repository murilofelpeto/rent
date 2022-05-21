package com.felpeto.rent.adapter.output.mysql.mapper;

import static com.felpeto.rent.adapter.output.mysql.mapper.PropertyMapper.toProperty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;

import com.felpeto.rent.adapter.output.mysql.entity.PropertyEntity;
import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.domain.vo.PropertyKind;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PropertyMapperTest {

  private final Faker faker = new Faker();

  @Test
  void givenPropertyEntityWhenMappingThenReturnProperty() {
    final var entity = createPropertyEntity();

    final var property = toProperty(entity);

    assertThat(property).isNotNull();
    assertThat(property.getPropertyKind()).isEqualTo(entity.getPropertyKind());
    assertThat(property.getUuid()).isEqualTo(entity.getUuid());
    assertThat(property.getTenant().getValue()).isEqualTo(entity.getTenant());
    assertThat(property.getAddress()).isNotNull().satisfies(address -> {
      assertThat(address.getZipCode().getValue()).isEqualTo(entity.getZipcode());
      assertThat(address.getState().getValue()).isEqualTo(entity.getState());
      assertThat(address.getStreetName().getValue()).isEqualTo(entity.getStreetName());
      assertThat(address.getNumber().getValue()).isEqualTo(entity.getHouseNumber());
      assertThat(address.getCity().getValue()).isEqualTo(entity.getCity());
      assertThat(address.getCountry().getValue()).isEqualTo(entity.getCountry());
      assertThat(address.getComplement()).isEqualTo(entity.getComplement());
    });
  }

  @Test
  void givenListOfPropertiesEntityWhenMappingThenReturnProperty() {
    final var entities = List.of(createPropertyEntity(), createPropertyEntity());

    final var property = toProperty(entities);

    assertThat(property).isNotNull()
        .asInstanceOf(list(Property.class))
        .hasSize(2);

  }

  private PropertyEntity createPropertyEntity() {
    return PropertyEntity.builder()
        .zipcode(faker.address().zipCode())
        .uuid(UUID.randomUUID())
        .tenant(faker.name().username())
        .streetName(faker.address().streetName())
        .state(faker.address().stateAbbr())
        .houseNumber(faker.number().numberBetween(1, 9999))
        .propertyKind(faker.options().option(PropertyKind.class))
        .country(faker.country().name())
        .complement(faker.programmingLanguage().name())
        .city(faker.address().city())
        .id(faker.number().numberBetween(1L, 9999L))
        .build();
  }
}