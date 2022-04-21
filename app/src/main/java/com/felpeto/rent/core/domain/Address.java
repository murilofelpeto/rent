package com.felpeto.rent.core.domain;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

import com.felpeto.rent.core.domain.vo.City;
import com.felpeto.rent.core.domain.vo.Country;
import com.felpeto.rent.core.domain.vo.HouseNumber;
import com.felpeto.rent.core.domain.vo.State;
import com.felpeto.rent.core.domain.vo.StreetName;
import com.felpeto.rent.core.domain.vo.ZipCode;

public record Address(Country country,
                      State state,
                      City city,
                      ZipCode zipCode,
                      StreetName streetName,
                      HouseNumber number,
                      String complement) {

  private static final String MANDATORY_FIELD = "Address | Field {0} is mandatory";

  public Address(
      final Country country,
      final State state,
      final City city,
      final ZipCode zipCode,
      final StreetName streetName,
      final HouseNumber number,
      final String complement) {

    this.country = requireNonNull(country, format(MANDATORY_FIELD, "country"));
    this.state = requireNonNull(state, format(MANDATORY_FIELD, "state"));
    this.city = requireNonNull(city, format(MANDATORY_FIELD, "city"));
    this.zipCode = requireNonNull(zipCode, format(MANDATORY_FIELD, "zipCode"));
    this.streetName = requireNonNull(streetName, format(MANDATORY_FIELD, "streetName"));
    this.number = requireNonNull(number, format(MANDATORY_FIELD, "number"));
    this.complement = complement;
  }
}
