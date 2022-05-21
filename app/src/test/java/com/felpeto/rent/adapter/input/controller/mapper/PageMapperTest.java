package com.felpeto.rent.adapter.input.controller.mapper;

import static com.felpeto.rent.adapter.input.controller.mapper.PageMapper.toPage;
import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.rent.adapter.input.controller.dto.request.PageDto;
import com.felpeto.rent.core.domain.vo.SortMode;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

class PageMapperTest {

  private final Faker faker = new Faker();

  @Test
  void givenPageDtoWhenMappingThenReturnPage() {
    final var pageDto = PageDto.builder()
        .sort("+sort")
        .offset(faker.number().numberBetween(0, 999))
        .limit(faker.number().numberBetween(1, 50))
        .build();

    final var page = toPage(pageDto);

    assertThat(page).isNotNull();
    assertThat(page.getLimit()).isEqualTo(pageDto.getLimit());
    assertThat(page.getOffset()).isEqualTo(pageDto.getOffset());
    assertThat(page.getSort()).isEqualTo("sort");
    assertThat(page.getSortMode()).isEqualTo(SortMode.ASC);
  }
}