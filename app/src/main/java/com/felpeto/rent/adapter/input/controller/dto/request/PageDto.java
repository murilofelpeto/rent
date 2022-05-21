package com.felpeto.rent.adapter.input.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
  @Min(value = 1, message = "query_parameter")
  @Max(value = 50, message = "query_parameter")
  @QueryParam("_limit")
  @DefaultValue("10")
  private int limit;


  @Schema(name = "_offset")
  @Min(value = 0, message = "query_parameter")
  @QueryParam("_offset")
  @DefaultValue("0")
  private int offset;

  @Schema(name = "_sort")
  @QueryParam("_sort")
  private String sort;
}
