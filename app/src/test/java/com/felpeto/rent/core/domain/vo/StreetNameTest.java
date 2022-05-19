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

class StreetNameTest {

  private static final String MANDATORY_FIELD = "StreetName | value is mandatory";
  private final Faker faker = new Faker();

  private static Stream<String> invalidValues() {
    return Stream.of("", "   ", null);
  }

  @Test
  void validToString() {
    ToStringVerifier
        .forClass(StreetName.class)
        .verify();
  }

  @Test
  void validHashCode() {
    EqualsVerifier.simple()
        .forClass(StreetName.class)
        .verify();
  }

  @Test
  void givenStreetNameNameWhenBuildStreetNameThenReturnValidStreetName() {
    final var streetName = faker.address().streetName();
    final var street = StreetName.of(streetName);

    assertThat(street.getValue()).isEqualTo(streetName);
  }

  @ParameterizedTest
  @MethodSource("invalidValues")
  void givenInvalidValueWhenBuildStreetNameThenThrowException(final String street) {
    assertThatThrownBy(() -> StreetName.of(street))
        .isExactlyInstanceOf(RuntimeException.class)
        .hasMessage(MANDATORY_FIELD);
  }
}