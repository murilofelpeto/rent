package com.felpeto.rent.adapter.input.controller;

import static com.felpeto.rent.adapter.input.controller.mapper.PropertyMapper.toProperty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.mockito.Mockito.when;

import com.felpeto.rent.adapter.input.controller.dto.request.PropertyRequestDto;
import com.felpeto.rent.adapter.input.controller.dto.response.PropertyResponseDto;
import com.felpeto.rent.core.usecase.CreatePropertyUseCase;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyControllerTest {

  private static final List<String> PROPERTIES_KINDS = List.of(
      "single-family",
      "public",
      "land",
      "warehouses");

  private final Faker faker = new Faker(new Locale("pt-BR"));

  @Mock
  private CreatePropertyUseCase createPropertyUseCase;

  @InjectMocks
  private PropertyController controller;

  @Test
  @DisplayName("given property request when create property then return created property")
  void givenPropertyRequestWhenCreatePropertyThenReturnCreatedProperty() {
    final var request = createPropertyRequest();
    final var property = toProperty(request);

    when(createPropertyUseCase.createProperty(property)).thenReturn(property);

    final var response = controller.createProperty(request);

    assertThat(response.getStatus()).isEqualTo(201);
    assertThat(response.getEntity()).isNotNull().isInstanceOf(PropertyResponseDto.class);

  }

  @Test
  @DisplayName("given property request when find all property then return list of properties")
  void givenPropertyRequestWhenFindAllPropertyThenReturnListOfProperties() {
    final var response = controller.getProperties();

    assertThat(response.getStatus()).isEqualTo(200);
    assertThat(response.getEntity()).isNotNull()
        .asInstanceOf(list(PropertyResponseDto.class));

  }

  @Test
  @DisplayName("given property request when find by id property then return created property")
  void givenPropertyRequestWhenFindAByIdPropertyThenReturnProperty() {
    final var response = controller.getPropertyById(UUID.randomUUID().toString());

    assertThat(response.getStatus()).isEqualTo(200);
    assertThat(response.getEntity()).isNotNull().isInstanceOf(PropertyResponseDto.class);
  }

  @Test
  @DisplayName("given property request when update property then return updated property")
  void givenPropertyRequestWhenUpdatePropertyThenReturnUpdatedProperty() {
    final var request = createPropertyRequest();
    final var response = controller.updateProperty(UUID.randomUUID().toString(), request);

    assertThat(response.getStatus()).isEqualTo(200);
    assertThat(response.getEntity()).isNotNull().isInstanceOf(PropertyResponseDto.class);
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