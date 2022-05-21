package com.felpeto.rent.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.felpeto.rent.core.domain.Address;
import com.felpeto.rent.core.domain.Page;
import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.domain.vo.City;
import com.felpeto.rent.core.domain.vo.Country;
import com.felpeto.rent.core.domain.vo.HouseNumber;
import com.felpeto.rent.core.domain.vo.PropertyKind;
import com.felpeto.rent.core.domain.vo.State;
import com.felpeto.rent.core.domain.vo.StreetName;
import com.felpeto.rent.core.domain.vo.Tenant;
import com.felpeto.rent.core.domain.vo.ZipCode;
import com.felpeto.rent.core.usecase.port.GetPropertyPort;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyGetterUseCaseTest {

  private final Faker faker = new Faker();

  @Mock
  private GetPropertyPort getPropertyPort;

  @InjectMocks
  private PropertyGetterUseCase propertyGetterUseCase;

  @Test
  void givenPageWhenGetPropertiesThenReturnListOfProperties() {
    final var page = createPage();
    final var properties = List.of(createProperty());

    when(getPropertyPort.getAllProperties(page)).thenReturn(properties);

    final var response = propertyGetterUseCase.getProperties(page);

    assertThat(response).isNotNull().isNotEmpty();

    verify(getPropertyPort).getAllProperties(page);
    verifyNoMoreInteractions(getPropertyPort);
  }

  private Property createProperty() {
    final var address = buildAddress();
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var uuid = UUID.randomUUID();
    final var tenant = Tenant.of(faker.name().username());

    return new Property(uuid, propertyKind, address, tenant);
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

  private Page createPage() {
    final var limit = faker.number().numberBetween(1, 50);
    final var offset = faker.number().numberBetween(0, 999);
    final var sort = "+sort";

    return Page.from(limit, offset, sort);
  }
}