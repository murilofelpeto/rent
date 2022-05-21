package com.felpeto.rent.adapter.input.controller;

import static com.felpeto.rent.adapter.input.controller.mapper.PageMapper.toPage;
import static com.felpeto.rent.adapter.input.controller.mapper.PropertyMapper.toProperty;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.felpeto.rent.adapter.input.controller.dto.request.PageDto;
import com.felpeto.rent.adapter.input.controller.dto.request.PropertyRequestDto;
import com.felpeto.rent.adapter.input.controller.dto.response.PropertyResponseDto;
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
import com.felpeto.rent.core.usecase.PropertyCreatorUseCase;
import com.felpeto.rent.core.usecase.PropertyGetterUseCase;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyControllerTest {

  private static final List<String> PROPERTIES_KINDS = List.of("single-family", "public", "land",
      "warehouses");

  private final Faker faker = new Faker(new Locale("pt-BR"));

  @Mock
  private PropertyCreatorUseCase propertyCreatorUseCase;

  @Mock
  private PropertyGetterUseCase propertyGetterUseCase;

  @InjectMocks
  private PropertyController controller;

  @Test
  void givenPropertyRequestWhenCreatePropertyThenReturnCreatedProperty() {
    final var request = createPropertyRequest();
    final var property = toProperty(request);

    when(propertyCreatorUseCase.createProperty(property)).thenReturn(property);

    final var response = controller.createProperty(request);

    assertThat(response.getStatus()).isEqualTo(201);
    assertThat(response.getEntity()).isNotNull().isInstanceOf(PropertyResponseDto.class);

    verify(propertyCreatorUseCase).createProperty(property);
    verifyNoInteractions(propertyGetterUseCase);
    verifyNoMoreInteractions(propertyCreatorUseCase);

  }

  @Test
  void givenPropertyRequestWhenFindAllPropertyThenReturnListOfProperties() {
    final var pageDto = createPageDto();

    final var page = toPage(pageDto);
    final var properties = List.of(createProperty());

    when(propertyGetterUseCase.getProperties(page)).thenReturn(properties);

    final var response = controller.getProperties(pageDto);

    assertThat(response.getStatus()).isEqualTo(200);

    verify(propertyGetterUseCase).getProperties(page);
    verifyNoInteractions(propertyCreatorUseCase);
    verifyNoMoreInteractions(propertyGetterUseCase);

  }

  @Test
  void givenPropertyRequestWhenFindAllPropertyThenReturnEmptyList() {
    final var pageDto = createPageDto();
    final var page = toPage(pageDto);

    when(propertyGetterUseCase.getProperties(page)).thenReturn(emptyList());

    final var response = controller.getProperties(pageDto);

    assertThat(response.getStatus()).isEqualTo(204);

    verify(propertyGetterUseCase).getProperties(page);
    verifyNoInteractions(propertyCreatorUseCase);
    verifyNoMoreInteractions(propertyGetterUseCase);

  }

  @Test
  void givenPropertyRequestWhenFindAByIdPropertyThenReturnProperty() {
    final var uuid = UUID.randomUUID();

    when(propertyGetterUseCase.getPropertyByUuid(uuid)).thenReturn(createProperty());

    final var response = controller.getPropertyById(uuid.toString());

    assertThat(response.getStatus()).isEqualTo(200);

    verify(propertyGetterUseCase).getPropertyByUuid(uuid);
    verifyNoInteractions(propertyCreatorUseCase);
    verifyNoMoreInteractions(propertyGetterUseCase);
  }

  @Test
  void givenPropertyRequestWhenUpdatePropertyThenReturnUpdatedProperty() {
    final var request = createPropertyRequest();
    final var response = controller.updateProperty(UUID.randomUUID().toString(), request);

    assertThat(response.getStatus()).isEqualTo(200);
    assertThat(response.getEntity()).isNotNull().isInstanceOf(PropertyResponseDto.class);
  }

  private Property createProperty() {
    return Property.builder().address(createAddress())
        .propertyKind(faker.options().option(PropertyKind.class)).tenant(Tenant.of("test"))
        .uuid(UUID.randomUUID()).build();
  }

  private Address createAddress() {
    final var country = Country.of(faker.address().country());
    final var state = State.of(faker.address().stateAbbr());
    final var city = City.of(faker.address().city());
    final var zipCode = ZipCode.of(faker.address().zipCode());
    final var streetName = StreetName.of(faker.address().streetName());
    final var houseNumber = HouseNumber.of(faker.number().numberBetween(1, 9999));
    final var complement = faker.address().secondaryAddress();

    return new Address(country, state, city, zipCode, streetName, houseNumber, complement);
  }

  private PropertyRequestDto createPropertyRequest() {
    return PropertyRequestDto.builder().city(faker.address().city())
        .complement(faker.address().secondaryAddress()).country(faker.address().country())
        .propertyKind(faker.options().nextElement(PROPERTIES_KINDS))
        .number(faker.number().numberBetween(1, 1500)).state(faker.address().stateAbbr())
        .streetName(faker.address().streetName()).zipCode(faker.address().zipCode()).build();
  }

  private PageDto createPageDto() {
    return PageDto.builder()
        .limit(faker.number().numberBetween(1, 50))
        .offset(faker.number().numberBetween(0, 999))
        .sort("+propertyKind")
        .build();
  }

}