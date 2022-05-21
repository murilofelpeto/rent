package com.felpeto.rent.adapter.output.mysql;

import static com.felpeto.rent.adapter.output.mysql.mapper.PropertyEntityMapper.toEntity;
import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
import com.github.javafaker.Faker;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdatePropertyGatewayTest {

  private static final String UPDATE_ERROR = "Error to update property";
  private static final String PROPERTY_NOT_FOUND = "Property with id {0} not found";
  private static final String UPDATE_QUERY = "zipcode = ?1,"
      + "property_kind = ?2,"
      + "country = ?3,"
      + "state = ?4,"
      + "city = ?5,"
      + "street_name = ?6,"
      + "complement = ?7 "
      + "where uuid = ?8";

  private final Faker faker = new Faker();

  @Mock
  private PropertyRepository propertyRepository;

  @Mock
  private EntityManager entityManager;

  @InjectMocks
  private UpdatePropertyGateway updatePropertyGateway;

  @Test
  void givenUuidPropertyWhenUpdateThenReturnProperty() {
    final var property = buildProperty();
    final var uuid = property.getUuid();
    final var propertyEntity = toEntity(property);

    when(propertyRepository.findByUuid(uuid)).thenReturn(Optional.of(propertyEntity));

    when(propertyRepository.update(UPDATE_QUERY,
        property.getAddress().getZipCode().getValue(),
        property.getPropertyKind().name(),
        property.getAddress().getCountry().getValue(),
        property.getAddress().getState().getValue(),
        property.getAddress().getCity().getValue(),
        property.getAddress().getStreetName().getValue(),
        property.getAddress().getComplement(),
        uuid))
        .thenReturn(1);

    when(propertyRepository.getEntityManager()).thenReturn(entityManager);
    doNothing()
        .when(entityManager)
        .refresh(propertyEntity);

    final var response = updatePropertyGateway.updateProperty(uuid, property);

    assertThat(response).isNotNull();

    verify(propertyRepository).findByUuid(uuid);
    verify(propertyRepository).update(UPDATE_QUERY,
        property.getAddress().getZipCode().getValue(),
        property.getPropertyKind().name(),
        property.getAddress().getCountry().getValue(),
        property.getAddress().getState().getValue(),
        property.getAddress().getCity().getValue(),
        property.getAddress().getStreetName().getValue(),
        property.getAddress().getComplement(),
        uuid);
    verify(propertyRepository).getEntityManager();
    verify(entityManager).refresh(propertyEntity);

    verifyNoMoreInteractions(propertyRepository, entityManager);
  }

  @Test
  void givenInvalidUuidPropertyWhenUpdateThenThrowException() {
    final var property = buildProperty();
    final var uuid = property.getUuid();

    when(propertyRepository.findByUuid(uuid)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> updatePropertyGateway.updateProperty(uuid, property))
        .hasMessage(format(PROPERTY_NOT_FOUND, uuid))
        .isExactlyInstanceOf(RuntimeException.class);

    verify(propertyRepository).findByUuid(uuid);
    verifyNoMoreInteractions(propertyRepository);
    verifyNoInteractions(entityManager);
  }

  @Test
  void givenUuidPropertyWhenUpdateMoreThanOnePropertyThenThrowException() {
    final var property = buildProperty();
    final var uuid = property.getUuid();
    final var propertyEntity = toEntity(property);

    when(propertyRepository.findByUuid(uuid)).thenReturn(Optional.of(propertyEntity));

    when(propertyRepository.update(UPDATE_QUERY,
        property.getAddress().getZipCode().getValue(),
        property.getPropertyKind().name(),
        property.getAddress().getCountry().getValue(),
        property.getAddress().getState().getValue(),
        property.getAddress().getCity().getValue(),
        property.getAddress().getStreetName().getValue(),
        property.getAddress().getComplement(),
        uuid))
        .thenReturn(2);

    assertThatThrownBy(() -> updatePropertyGateway.updateProperty(uuid, property))
        .hasMessage(UPDATE_ERROR)
        .isExactlyInstanceOf(RuntimeException.class);

    verify(propertyRepository).findByUuid(uuid);
    verify(propertyRepository).update(UPDATE_QUERY, property.getAddress().getZipCode().getValue(),
        property.getPropertyKind().name(),
        property.getAddress().getCountry().getValue(),
        property.getAddress().getState().getValue(),
        property.getAddress().getCity().getValue(),
        property.getAddress().getStreetName().getValue(),
        property.getAddress().getComplement(),
        uuid);

    verify(propertyRepository).findByUuid(uuid);
    verify(propertyRepository).update(UPDATE_QUERY,
        property.getAddress().getZipCode().getValue(),
        property.getPropertyKind().name(),
        property.getAddress().getCountry().getValue(),
        property.getAddress().getState().getValue(),
        property.getAddress().getCity().getValue(),
        property.getAddress().getStreetName().getValue(),
        property.getAddress().getComplement(),
        uuid);
    verifyNoMoreInteractions(propertyRepository);
    verifyNoInteractions(entityManager);
  }

  @Test
  void givenUuidPropertyWhenUpdateLessThanOnePropertyThenThrowException() {
    final var property = buildProperty();
    final var uuid = property.getUuid();
    final var propertyEntity = toEntity(property);

    when(propertyRepository.findByUuid(uuid)).thenReturn(Optional.of(propertyEntity));

    when(propertyRepository.update(UPDATE_QUERY,
        property.getAddress().getZipCode().getValue(),
        property.getPropertyKind().name(),
        property.getAddress().getCountry().getValue(),
        property.getAddress().getState().getValue(),
        property.getAddress().getCity().getValue(),
        property.getAddress().getStreetName().getValue(),
        property.getAddress().getComplement(),
        uuid))
        .thenReturn(0);

    assertThatThrownBy(() -> updatePropertyGateway.updateProperty(uuid, property))
        .hasMessage(UPDATE_ERROR)
        .isExactlyInstanceOf(RuntimeException.class);

    verify(propertyRepository).findByUuid(uuid);
    verify(propertyRepository).update(UPDATE_QUERY, property.getAddress().getZipCode().getValue(),
        property.getPropertyKind().name(),
        property.getAddress().getCountry().getValue(),
        property.getAddress().getState().getValue(),
        property.getAddress().getCity().getValue(),
        property.getAddress().getStreetName().getValue(),
        property.getAddress().getComplement(),
        uuid);

    verify(propertyRepository).findByUuid(uuid);
    verify(propertyRepository).update(UPDATE_QUERY,
        property.getAddress().getZipCode().getValue(),
        property.getPropertyKind().name(),
        property.getAddress().getCountry().getValue(),
        property.getAddress().getState().getValue(),
        property.getAddress().getCity().getValue(),
        property.getAddress().getStreetName().getValue(),
        property.getAddress().getComplement(),
        uuid);
    verifyNoMoreInteractions(propertyRepository);
    verifyNoInteractions(entityManager);
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