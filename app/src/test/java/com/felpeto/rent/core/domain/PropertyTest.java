package com.felpeto.rent.core.domain;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.felpeto.rent.core.domain.vo.City;
import com.felpeto.rent.core.domain.vo.Country;
import com.felpeto.rent.core.domain.vo.HouseNumber;
import com.felpeto.rent.core.domain.vo.PropertyKind;
import com.felpeto.rent.core.domain.vo.State;
import com.felpeto.rent.core.domain.vo.StreetName;
import com.felpeto.rent.core.domain.vo.Tenant;
import com.felpeto.rent.core.domain.vo.ZipCode;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.UUID;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PropertyTest {

  private static final String MANDATORY_FIELD = "Property | Field {0} is mandatory";
  private static final Faker faker = new Faker();

  private static Stream<Arguments> invalidParamsWithUuid() {
    final var address = buildAddress();
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var uuid = UUID.randomUUID();
    final var tenant = Tenant.of(faker.name().username());

    return Stream.of(
        arguments(null, propertyKind, address, tenant, format(MANDATORY_FIELD, "uuid")),
        arguments(uuid, null, address, tenant, format(MANDATORY_FIELD, "propertyKind")),
        arguments(uuid, propertyKind, null, tenant, format(MANDATORY_FIELD, "address")),
        arguments(uuid, propertyKind, address, null, format(MANDATORY_FIELD, "tenant")));
  }

  private static Stream<Arguments> invalidParamsWithoutUuid() {
    final var address = buildAddress();
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var tenant = Tenant.of(faker.name().username());

    return Stream.of(
        arguments(null, address, tenant, format(MANDATORY_FIELD, "propertyKind")),
        arguments(propertyKind, null, tenant, format(MANDATORY_FIELD, "address")),
        arguments(propertyKind, address, null, format(MANDATORY_FIELD, "tenant")));
  }

  private static Address buildAddress() {
    final var country = Country.of(faker.address().country());
    final var state = State.of(faker.address().stateAbbr());
    final var city = City.of(faker.address().city());
    final var zipCode = ZipCode.of(faker.address().zipCode());
    final var streetName = StreetName.of(faker.address().streetName());
    final var houseNumber = HouseNumber.of(faker.number().numberBetween(1, 9999));
    final var complement = faker.address().secondaryAddress();

    return new Address(country, state, city, zipCode, streetName, houseNumber, complement);
  }

  @Test
  void validToString() {
    ToStringVerifier
        .forClass(Property.class)
        .verify();
  }

  @Test
  void validHashCode() {
    EqualsVerifier
        .forClass(Property.class)
        .suppress(Warning.ZERO_FIELDS)
        .withNonnullFields("uuid", "propertyKind", "address", "tenant")
        .verify();
  }

  @Test
  void givenValidParametersWhenBuildPropertyThenReturnValidProperty() {
    final var address = buildAddress();
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var uuid = UUID.randomUUID();
    final var tenant = Tenant.of(faker.name().username());

    final var property = Property.builder()
        .propertyKind(propertyKind)
        .uuid(uuid)
        .address(address)
        .tenant(tenant)
        .build();

    assertThat(property).isNotNull();
    assertThat(property.getUuid()).isNotNull().isEqualTo(uuid);
    assertThat(property.getPropertyKind()).isNotNull().isEqualTo(propertyKind);
    assertThat(property.getAddress()).isNotNull().isEqualTo(address);
    assertThat(property.getTenant()).isNotNull().isEqualTo(tenant);
  }

  @Test
  void givenValidParametersWithoutUuidWhenBuildPropertyThenReturnValidProperty() {
    final var address = buildAddress();
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var tenant = Tenant.of(faker.name().username());

    final var property = Property.withoutUuid()
        .propertyKind(propertyKind)
        .address(address)
        .tenant(tenant)
        .create();

    assertThat(property).isNotNull();
    assertThat(property.getUuid()).isNull();
    assertThat(property.getPropertyKind()).isNotNull().isEqualTo(propertyKind);
    assertThat(property.getAddress()).isNotNull().isEqualTo(address);
    assertThat(property.getTenant()).isNotNull().isEqualTo(tenant);
  }

  @ParameterizedTest
  @MethodSource("invalidParamsWithUuid")
  void givenInvalidParametersWhenBuildPropertyThenThrowNullPointerException(
      final UUID uuid,
      final PropertyKind propertyKind,
      final Address address,
      final Tenant tenant,
      final String message) {

    assertThatThrownBy(() ->
        new Property(uuid, propertyKind, address, tenant))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage(message);
  }

  @ParameterizedTest
  @MethodSource("invalidParamsWithoutUuid")
  void givenInvalidParametersWithoutUuidWhenBuildPropertyThenThrowNullPointerException(
      final PropertyKind propertyKind,
      final Address address,
      final Tenant tenant,
      final String message) {

    assertThatThrownBy(() ->
        new Property(propertyKind, address, tenant))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage(message);
  }
}