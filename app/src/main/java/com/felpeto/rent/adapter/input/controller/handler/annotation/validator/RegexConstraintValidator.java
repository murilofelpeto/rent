package com.felpeto.rent.adapter.input.controller.handler.annotation.validator;

import com.felpeto.rent.adapter.input.controller.handler.annotation.Regex;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegexConstraintValidator implements ConstraintValidator<Regex, String> {

  private Pattern pattern;

  @Override
  public void initialize(final Regex regex) {
    pattern = Pattern.compile(regex.exp());
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    if (Objects.isNull(value)) {
      return true;
    }

    return pattern.matcher(value).matches();
  }
}
