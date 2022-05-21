package com.felpeto.rent.adapter.input.controller.mapper;

import com.felpeto.rent.adapter.input.controller.dto.request.PageDto;
import com.felpeto.rent.core.domain.Page;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageMapper {

  public static Page toPage(final PageDto dto) {
    return Page.from(dto.getLimit(), dto.getOffset(), dto.getSort());
  }
}
