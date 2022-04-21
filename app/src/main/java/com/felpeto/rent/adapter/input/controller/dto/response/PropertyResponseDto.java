package com.felpeto.rent.adapter.input.controller.dto.response;

import java.util.UUID;
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
public class PropertyResponseDto {

  private UUID id;
  private String propertyKind;
  private String country;
  private String state;
  private String city;
  private String zipCode;
  private String streetName;
  private Integer number;
  private String complement;
}
