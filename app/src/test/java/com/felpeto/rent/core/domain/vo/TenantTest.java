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

class TenantTest {

  private static final String MANDATORY_FIELD = "Tenant | value is mandatory";
  private final Faker faker = new Faker();

  private static Stream<String> invalidValues() {
    return Stream.of("", "   ", null);
  }

  @Test
  void validToString() {
    ToStringVerifier
        .forClass(Tenant.class)
        .verify();
  }

  @Test
  void validHashCode() {
    EqualsVerifier.simple()
        .forClass(Tenant.class)
        .verify();
  }

  @Test
  void givenTenantNameWhenBuildTenantThenReturnValidTenant() {
    final var tenantName = faker.name().username();
    final var tenant = Tenant.of(tenantName);

    assertThat(tenant.getValue()).isEqualTo(tenantName);
  }

  @ParameterizedTest
  @MethodSource("invalidValues")
  void givenInvalidValueWhenBuildTenantThenThrowException(final String tenant) {
    assertThatThrownBy(() -> Tenant.of(tenant))
        .isExactlyInstanceOf(RuntimeException.class)
        .hasMessage(MANDATORY_FIELD);
  }
}