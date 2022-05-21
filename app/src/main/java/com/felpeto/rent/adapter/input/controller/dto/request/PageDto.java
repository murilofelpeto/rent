package com.felpeto.rent.adapter.input.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

  @Schema(name = "_limit")
  @Min(value = 1, message = "_limit must be greater than 0")
  @Max(value = 50, message = "_limit must be less than or equal to 50")
  @QueryParam("_limit")
  @DefaultValue("10")
  private int limit;


  @Schema(name = "_offset")
  @Min(value = 0, message = "_offset must be greater than 0")
  @QueryParam("_offset")
  @DefaultValue("0")
  private int offset;

  @NotBlank(message = "_sort can't be null or empty")
  @Schema(name = "_sort")
  @QueryParam("_sort")
  private String sort;
}
