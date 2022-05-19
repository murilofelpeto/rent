package com.felpeto.rent.adapter.input.controller.dto.request;

import com.felpeto.rent.core.domain.vo.PropertyKind;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class PropertyRequestDto {

  @Schema(
      name = "property_kind",
      required = true,
      allowableValues = {
          "single-family",
          "multi-family",
          "townhouses",
          "condominiums",
          "apartments",
          "manufactured",
          "public",
          "retail",
          "office",
          "co-working",
          "land",
          "heavy-manufacturing",
          "light-manufacturing",
          "warehouses",
          "distribution-facilities"})
  private String propertyKind;

  @Schema(name = "country", required = true)
  private String country;

  @Schema(
      name = "state",
      required = true,
      minLength = 2,
      maxLength = 2,
      description = "We expect an abbreviation of the state")
  private String state;

  @Schema(name = "city", required = true)
  private String city;

  @Schema(name = "zip_code", required = true)
  private String zipCode;

  @Schema(name = "street_name", required = true)
  private String streetName;

  @Schema(name = "number", required = true)
  private Integer number;

  @Schema(name = "complement", required = true)
  private String complement;

}
