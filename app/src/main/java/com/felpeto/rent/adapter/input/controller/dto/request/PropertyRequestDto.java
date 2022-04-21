package com.felpeto.rent.adapter.input.controller.dto.request;

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

  private String propertyKind;
  private String country;
  private String state;
  private String city;
  private String zipCode;
  private String streetName;
  private Integer number;
  private String complement;

}
