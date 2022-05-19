package com.felpeto.rent.adapter.output.mysql.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PropertyEntityTest {

  @Test
  void givenPropertyEntityWhenCallPrePersistThenShouldHaveValidUuid() {
    final var build = PropertyEntity.builder().build();
    build.prePersist();
    assertThat(build.getUuid()).isNotNull();
  }
}