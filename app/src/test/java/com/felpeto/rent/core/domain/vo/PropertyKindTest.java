package com.felpeto.rent.core.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.github.javafaker.Faker;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PropertyKindTest {

  private static final List<String> RESIDENTIAL = List.of("single-family",
      "multi-family",
      "townhouses",
      "condominiums",
      "apartments",
      "manufactured");

  private static final List<String> COMMERCIAL = List.of("public",
      "retail",
      "office",
      "co-working");

  private static final List<String> LAND = List.of("land");

  private static final List<String> INDUSTRIAL = List.of("heavy-manufacturing",
      "light-manufacturing",
      "warehouses",
      "distribution-facilities");

  private static final String VALUE_NOT_FOUND = "PropertyKind | Desired value not found";

  private static final Faker faker = new Faker();

  private static Stream<Arguments> properties() {
    return Stream.of(
        arguments(faker.options().nextElement(RESIDENTIAL), PropertyKind.RESIDENTIAL, RESIDENTIAL),
        arguments(faker.options().nextElement(COMMERCIAL), PropertyKind.COMMERCIAL, COMMERCIAL),
        arguments(faker.options().nextElement(LAND), PropertyKind.LAND, LAND),
        arguments(faker.options().nextElement(INDUSTRIAL), PropertyKind.INDUSTRIAL, INDUSTRIAL));
  }

  private static Stream<Arguments> invalidParams() {
    return Stream.of(
        arguments(faker.hobbit().location(), IllegalArgumentException.class),
        arguments("", IllegalArgumentException.class),
        arguments("  ", IllegalArgumentException.class),
        arguments(null, NullPointerException.class));
  }

  @ParameterizedTest
  @MethodSource("properties")
  void givenValuesWhenCallOfThenReturnValidPropertyKind(
      final String kind,
      final PropertyKind propertyKind,
      final List<String> propertyKinds) {

    final var property = PropertyKind.of(kind);

    assertThat(property).isEqualTo(propertyKind);
    assertThat(property.getKind()).containsExactlyInAnyOrderElementsOf(propertyKinds);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  void givenInvalidValuesWhenCallOfThenThrowException(final String kind, Throwable exception) {
    assertThatThrownBy(() -> PropertyKind.of(kind))
        .isExactlyInstanceOf(exception.getClass())
        .hasMessage(VALUE_NOT_FOUND);
  }
}