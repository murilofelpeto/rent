package com.felpeto.rent.adapter.input.controller.handler.annotation.validator;

import com.felpeto.rent.adapter.input.controller.handler.annotation.OfEnum;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OfEnumValidator implements ConstraintValidator<OfEnum, CharSequence> {

  private List<String> acceptedValues;

  @Override
  public void initialize(OfEnum annotation) {
    acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
        .map(Enum::toString)
        .collect(Collectors.toList());
  }

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    if (Objects.isNull(value)) {
      return true;
    }

    return acceptedValues.contains(value.toString());
  }
}