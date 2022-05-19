package com.felpeto.rent.adapter.input.controller.mapper;

import static com.felpeto.rent.adapter.input.controller.mapper.PropertyMapper.toProperty;
import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.rent.adapter.input.controller.dto.request.PropertyRequestDto;
import com.github.javafaker.Faker;
import java.util.List;
import org.junit.jupiter.api.Test;

class PropertyMapperTest {

  private static final List<String> PROPERTIES_KINDS = List.of(
      "single-family",
      "public",
      "land",
      "warehouses");

  private final Faker faker = new Faker();

  @Test
  void givenPropertyRequestWhenMapThenReturnProperty() {
    final var request = createPropertyRequest();

    final var property = toProperty(request);

    assertThat(property).isNotNull();
    assertThat(property.getUuid()).isNull();
    assertThat(property.getPropertyKind().getKind()).contains(request.getPropertyKind());
    assertThat(property.getTenant().getValue()).isEqualTo("Teste");
    assertThat(property.getAddress()).isNotNull()
        .satisfies(address -> {
          assertThat(address.getCity().getValue()).isEqualTo(request.getCity());
          assertThat(address.getComplement()).isEqualTo(request.getComplement());
          assertThat(address.getCountry().getValue()).isEqualTo(request.getCountry());
          assertThat(address.getNumber().getValue()).isEqualTo(request.getNumber());
          assertThat(address.getState().getValue()).isEqualTo(request.getState());
          assertThat(address.getStreetName().getValue()).isEqualTo(request.getStreetName());
          assertThat(address.getZipCode().getValue()).isEqualTo(request.getZipCode());
        });
  }

  private PropertyRequestDto createPropertyRequest() {
    return PropertyRequestDto.builder()
        .city(faker.address().city())
        .complement(faker.address().secondaryAddress())
        .country(faker.address().country())
        .propertyKind(faker.options().nextElement(PROPERTIES_KINDS))
        .number(faker.number().numberBetween(1, 1500))
        .state(faker.address().stateAbbr())
        .streetName(faker.address().streetName())
        .zipCode(faker.address().zipCode())
        .build();
  }
}