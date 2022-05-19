package com.felpeto.rent.core.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class HouseNumberTest {

  private static final String MANDATORY_FIELD = "HouseNumber | value is mandatory";
  private final Faker faker = new Faker();

  @Test
  void validToString() {
    ToStringVerifier
        .forClass(HouseNumber.class)
        .verify();
  }

  @Test
  void validHashCode() {
    EqualsVerifier.simple()
        .forClass(HouseNumber.class)
        .verify();
  }

  @Test
  void givenHouseNumberNameWhenBuildHouseNumberThenReturnValidHouseNumber() {
    final var number = faker.number().numberBetween(1, 9999999);
    final var houseNumber = HouseNumber.of(number);

    assertThat(houseNumber.getValue()).isEqualTo(number);
  }

  @Test
  void givenInvalidValueWhenBuildHouseNumberThenThrowException() {
    assertThatThrownBy(() -> HouseNumber.of(null))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage(MANDATORY_FIELD);
  }

}