package com.felpeto.rent.core.domain;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.felpeto.rent.core.domain.vo.City;
import com.felpeto.rent.core.domain.vo.Country;
import com.felpeto.rent.core.domain.vo.HouseNumber;
import com.felpeto.rent.core.domain.vo.State;
import com.felpeto.rent.core.domain.vo.StreetName;
import com.felpeto.rent.core.domain.vo.ZipCode;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AddressTest {

  private static final String MANDATORY_FIELD = "Address | Field {0} is mandatory";
  private static final Faker faker = new Faker();

  private static Stream<Arguments> invalidParams() {
    final var country = Country.of(faker.address().country());
    final var state = State.of(faker.address().stateAbbr());
    final var city = City.of(faker.address().city());
    final var zipCode = ZipCode.of(faker.address().zipCode());
    final var streetName = StreetName.of(faker.address().streetName());
    final var houseNumber = HouseNumber.of(faker.number().numberBetween(1, 9999));
    final var complement = faker.address().secondaryAddress();

    return Stream.of(
        arguments(null, state, city, zipCode, streetName, houseNumber, complement,
            format(MANDATORY_FIELD, "country")),
        arguments(country, null, city, zipCode, streetName, houseNumber, complement,
            format(MANDATORY_FIELD, "state")),
        arguments(country, state, null, zipCode, streetName, houseNumber, complement,
            format(MANDATORY_FIELD, "city")),
        arguments(country, state, city, null, streetName, houseNumber, complement,
            format(MANDATORY_FIELD, "zipCode")),
        arguments(country, state, city, zipCode, null, houseNumber, complement,
            format(MANDATORY_FIELD, "streetName")),
        arguments(country, state, city, zipCode, streetName, null, complement,
            format(MANDATORY_FIELD, "number")));
  }

  @Test
  void validToString() {
    ToStringVerifier
        .forClass(Address.class)
        .verify();
  }

  @Test
  void validHashCode() {
    EqualsVerifier
        .forClass(Address.class)
        .suppress(Warning.ZERO_FIELDS)
        .withNonnullFields("country", "state", "city", "zipCode", "streetName", "number")
        .verify();
  }

  @Test
  void givenValidParametersWhenBuildAddressThenReturnAddress() {
    final var country = Country.of(faker.address().country());
    final var state = State.of(faker.address().stateAbbr());
    final var city = City.of(faker.address().city());
    final var zipCode = ZipCode.of(faker.address().zipCode());
    final var streetName = StreetName.of(faker.address().streetName());
    final var houseNumber = HouseNumber.of(faker.number().numberBetween(1, 9999));
    final var complement = faker.address().secondaryAddress();

    final var address = new Address(
        country,
        state,
        city,
        zipCode,
        streetName,
        houseNumber,
        complement);

    assertThat(address).isNotNull();
    assertThat(address.getCountry()).isNotNull().isEqualTo(country);
    assertThat(address.getState()).isNotNull().isEqualTo(state);
    assertThat(address.getCity()).isNotNull().isEqualTo(city);
    assertThat(address.getZipCode()).isNotNull().isEqualTo(zipCode);
    assertThat(address.getStreetName()).isNotNull().isEqualTo(streetName);
    assertThat(address.getNumber()).isNotNull().isEqualTo(houseNumber);
    assertThat(address.getComplement()).isNotNull().isEqualTo(complement);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  void givenInvalidParametersWhenBuildAddressThenThrowNullPointerException(
      final Country country,
      final State state,
      final City city,
      final ZipCode zipCode,
      final StreetName streetName,
      final HouseNumber number,
      final String complement,
      final String message) {

    assertThatThrownBy(() ->
        new Address(country, state, city, zipCode, streetName, number, complement))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage(message);

  }
}