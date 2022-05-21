package com.felpeto.rent.adapter.output.mysql;

import static com.felpeto.rent.adapter.output.mysql.mapper.PropertyEntityMapper.toEntity;
import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.felpeto.rent.adapter.output.mysql.repository.PropertyRepository;
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
import com.felpeto.rent.core.usecase.exception.DuplicateEntityException;
import com.github.javafaker.Faker;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;
import java.util.stream.Stream;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreatePropertyGatewayTest {

  private static final String ADDRESS_CONSTRAINT_NAME = "property.uc_property_entity_address";
  private static final String UUID_CONSTRAINT_NAME = "property.uc_property_uuid";
  private static final String PROPERTY_EXISTS = "Property already exists for uuid {0}";
  private static final String HIBERNATE_CONSTRAINT_EXCEPTION = "org.hibernate.exception.ConstraintViolationException: Duplicate key";
  private final Faker faker = new Faker();

  @Mock
  private PropertyRepository propertyRepository;

  @InjectMocks
  private CreatePropertyGateway createPropertyGateway;

  private static Stream<String> duplicateEntry() {
    return Stream.of(UUID_CONSTRAINT_NAME, ADDRESS_CONSTRAINT_NAME);
  }

  @Test
  void givenPropertyWhenCreateThenPersists() {
    final var property = buildProperty();
    final var entity = toEntity(property);

    doNothing()
        .when(propertyRepository)
        .persistAndFlush(entity);

    final var response = createPropertyGateway.create(property);

    assertThat(response).isNotNull();

    verify(propertyRepository).persistAndFlush(entity);
    verifyNoMoreInteractions(propertyRepository);
  }

  @ParameterizedTest
  @MethodSource("duplicateEntry")
  void givenDuplicatePropertyWhenCreateThenThrowDuplicateException(final String constraintName) {
    final var property = buildProperty();
    final var entity = toEntity(property);

    final var persistenceException =
        new PersistenceException(
            new ConstraintViolationException(
                "Duplicate key",
                new SQLIntegrityConstraintViolationException(),
                constraintName));

    doThrow(persistenceException)
        .when(propertyRepository)
        .persistAndFlush(entity);

    assertThatThrownBy(() -> createPropertyGateway.create(property))
        .isExactlyInstanceOf(DuplicateEntityException.class)
        .hasMessage(format(PROPERTY_EXISTS, property.getUuid()));

    verify(propertyRepository).persistAndFlush(entity);
    verifyNoMoreInteractions(propertyRepository);
  }

  @Test
  void givenPropertyWhenCreateThenThrowPersistenceException() {
    final var property = buildProperty();
    final var entity = toEntity(property);

    final var persistenceException =
        new PersistenceException(
            new ConstraintViolationException(
                "Duplicate key",
                new SQLIntegrityConstraintViolationException(),
                "UK84723947289711"));

    doThrow(persistenceException)
        .when(propertyRepository)
        .persistAndFlush(entity);

    assertThatThrownBy(() -> createPropertyGateway.create(property))
        .isExactlyInstanceOf(PersistenceException.class)
        .hasMessage(HIBERNATE_CONSTRAINT_EXCEPTION);

    verify(propertyRepository).persistAndFlush(entity);
    verifyNoMoreInteractions(propertyRepository);
  }

  @Test
  void givenPropertyWhenCreateThenThrowPersistenceExceptionWithoutCause() {
    final var property = buildProperty();
    final var entity = toEntity(property);

    doThrow(new PersistenceException())
        .when(propertyRepository)
        .persistAndFlush(entity);

    assertThatThrownBy(() -> createPropertyGateway.create(property))
        .isExactlyInstanceOf(PersistenceException.class)
        .hasMessage(null);

    verify(propertyRepository).persistAndFlush(entity);
    verifyNoMoreInteractions(propertyRepository);
  }

  private Property buildProperty() {
    final var address = buildAddress();
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var tenant = Tenant.of(faker.name().username());

    return Property.builder()
        .uuid(UUID.randomUUID())
        .propertyKind(propertyKind)
        .address(address)
        .tenant(tenant)
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