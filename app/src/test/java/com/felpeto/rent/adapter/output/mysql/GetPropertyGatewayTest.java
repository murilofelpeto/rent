package com.felpeto.rent.adapter.output.mysql;

import static java.text.MessageFormat.format;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.felpeto.rent.adapter.output.mysql.entity.PropertyEntity;
import com.felpeto.rent.adapter.output.mysql.repository.PropertyRepository;
import com.felpeto.rent.core.domain.Page;
import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.domain.vo.PropertyKind;
import com.felpeto.rent.core.usecase.exception.EntityNotFoundException;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetPropertyGatewayTest {

  private static final String PROPERTY_NOT_FOUND = "Property with id {0} not found";
  private final Faker faker = new Faker();

  @Mock
  private PropertyRepository propertyRepository;

  @InjectMocks
  private GetPropertyGateway getPropertyGateway;

  @Test
  void givenPageWhenFindAllPropertiesThenReturnListOfProperties() {
    final var limit = faker.number().numberBetween(1, 50);
    final var offset = faker.number().numberBetween(0, 999);
    final var sort = "+sort";

    final var page = Page.from(limit, offset, sort);
    final var propertyEntities = List.of(buildEntity(), buildEntity());

    when(propertyRepository.findAll(page)).thenReturn(propertyEntities);

    final var response = getPropertyGateway.getAllProperties(page);

    assertThat(response).isNotNull()
        .asInstanceOf(list(Property.class))
        .hasSize(2);

    verify(propertyRepository).findAll(page);
    verifyNoMoreInteractions(propertyRepository);
  }

  @Test
  void givenPageWhenFindAllPropertiesThenReturnEmptyList() {
    final var limit = faker.number().numberBetween(1, 50);
    final var offset = faker.number().numberBetween(0, 999);
    final var sort = "+sort";

    final var page = Page.from(limit, offset, sort);

    when(propertyRepository.findAll(page)).thenReturn(emptyList());

    final var response = getPropertyGateway.getAllProperties(page);

    assertThat(response).isEmpty();

    verify(propertyRepository).findAll(page);
    verifyNoMoreInteractions(propertyRepository);
  }

  @Test
  void givenUuidWhenFindByUuidThenReturnProperty() {
    final var uuid = UUID.randomUUID();
    final var entity = buildEntity();

    when(propertyRepository.findByUuid(uuid)).thenReturn(Optional.of(entity));

    final var response = getPropertyGateway.getPropertyByUuid(uuid);

    assertThat(response).isNotNull().isInstanceOf(Property.class);

    verify(propertyRepository).findByUuid(uuid);
    verifyNoMoreInteractions(propertyRepository);
  }

  @Test
  void givenInvalidUuidWhenFindByUuidThenThrowException() {
    final var uuid = UUID.randomUUID();

    when(propertyRepository.findByUuid(uuid)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> getPropertyGateway.getPropertyByUuid(uuid))
        .hasMessage(format(PROPERTY_NOT_FOUND, uuid))
        .isExactlyInstanceOf(EntityNotFoundException.class);

    verify(propertyRepository).findByUuid(uuid);
    verifyNoMoreInteractions(propertyRepository);
  }

  private PropertyEntity buildEntity() {
    return PropertyEntity.builder()
        .id(faker.number().numberBetween(1L, 99999L))
        .city(faker.address().city())
        .propertyKind(faker.options().option(PropertyKind.class))
        .complement(faker.educator().course())
        .country(faker.address().country())
        .houseNumber(faker.number().numberBetween(1, 9999))
        .state(faker.address().stateAbbr())
        .streetName(faker.address().streetName())
        .tenant(faker.name().username())
        .uuid(UUID.randomUUID())
        .zipcode(faker.address().zipCode())
        .build();
  }
}