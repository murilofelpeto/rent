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

class ZipCodeTest {

  private static final String MANDATORY_FIELD = "ZipCode | value is mandatory";
  private final Faker faker = new Faker();

  private static Stream<String> invalidValues() {
    return Stream.of("", "   ", null);
  }

  @Test
  void validToString() {
    ToStringVerifier
        .forClass(ZipCode.class)
        .verify();
  }

  @Test
  void validHashCode() {
    EqualsVerifier.simple()
        .forClass(ZipCode.class)
        .verify();
  }

  @Test
  void givenZipCodeNameWhenBuildZipCodeThenReturnValidZipCode() {
    final var zip = faker.address().zipCode();
    final var zipCode = ZipCode.of(zip);

    assertThat(zipCode.getValue()).isEqualTo(zip);
  }

  @ParameterizedTest
  @MethodSource("invalidValues")
  void givenInvalidValueWhenBuildZipCodeThenThrowException(final String zipCode) {
    assertThatThrownBy(() -> ZipCode.of(zipCode))
        .isExactlyInstanceOf(RuntimeException.class)
        .hasMessage(MANDATORY_FIELD);
  }
}