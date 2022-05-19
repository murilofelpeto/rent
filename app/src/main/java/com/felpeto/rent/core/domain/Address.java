package com.felpeto.rent.core.domain;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

import com.felpeto.rent.core.domain.vo.City;
import com.felpeto.rent.core.domain.vo.Country;
import com.felpeto.rent.core.domain.vo.HouseNumber;
import com.felpeto.rent.core.domain.vo.State;
import com.felpeto.rent.core.domain.vo.StreetName;
import com.felpeto.rent.core.domain.vo.ZipCode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@FieldNameConstants
public final class Address {

  private static final String MANDATORY_FIELD = "Address | Field {0} is mandatory";

  private final Country country;
  private final State state;
  private final City city;
  private final ZipCode zipCode;
  private final StreetName streetName;
  private final HouseNumber number;
  private final String complement;


  public Address(
      final Country country,
      final State state,
      final City city,
      final ZipCode zipCode,
      final StreetName streetName,
      final HouseNumber number,
      final String complement) {

    this.country = requireNonNull(country, format(MANDATORY_FIELD, Fields.country));
    this.state = requireNonNull(state, format(MANDATORY_FIELD, Fields.state));
    this.city = requireNonNull(city, format(MANDATORY_FIELD, Fields.city));
    this.zipCode = requireNonNull(zipCode, format(MANDATORY_FIELD, Fields.zipCode));
    this.streetName = requireNonNull(streetName, format(MANDATORY_FIELD, Fields.streetName));
    this.number = requireNonNull(number, format(MANDATORY_FIELD, Fields.number));
    this.complement = complement;
  }
}
