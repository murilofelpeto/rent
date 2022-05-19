package com.felpeto.rent.core.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CityTest {

  private static final String MANDATORY_FIELD = "City | value is mandatory";
  private final Faker faker = new Faker();

  private static Stream<String> invalidValues() {
    return Stream.of("", "   ", null);
  }

  @Test
  void validToString() {
    ToStringVerifier
        .forClass(City.class)
        .verify();
  }

  @Test
  void validHashCode() {
    EqualsVerifier.simple()
        .forClass(City.class)
        .verify();
  }

  @Test
  void givenCityNameWhenBuildCityThenReturnValidCity() {
    final var cityName = faker.address().cityName();
    final var city = City.of(cityName);

    assertThat(city.getValue()).isEqualTo(cityName);
  }

  @ParameterizedTest
  @MethodSource("invalidValues")
  void givenInvalidValueWhenBuildCityThenThrowException(final String cityName) {
    assertThatThrownBy(() -> City.of(cityName))
        .isExactlyInstanceOf(RuntimeException.class)
        .hasMessage(MANDATORY_FIELD);
  }

}