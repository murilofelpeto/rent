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

class StateTest {

  private static final String MANDATORY_FIELD = "State | value is mandatory";
  private final Faker faker = new Faker();

  private static Stream<String> invalidValues() {
    return Stream.of("", "   ", null);
  }

  @Test
  void validToString() {
    ToStringVerifier
        .forClass(State.class)
        .verify();
  }

  @Test
  void validHashCode() {
    EqualsVerifier.simple()
        .forClass(State.class)
        .verify();
  }

  @Test
  void givenStateNameWhenBuildStateThenReturnValidState() {
    final var stateName = faker.address().state();
    final var state = State.of(stateName);

    assertThat(state.getValue()).isEqualTo(stateName);
  }

  @ParameterizedTest
  @MethodSource("invalidValues")
  void givenInvalidValueWhenBuildStateThenThrowException(final String stateName) {
    assertThatThrownBy(() -> State.of(stateName))
        .isExactlyInstanceOf(RuntimeException.class)
        .hasMessage(MANDATORY_FIELD);
  }
}