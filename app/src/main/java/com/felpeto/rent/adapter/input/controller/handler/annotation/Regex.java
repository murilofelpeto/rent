package com.felpeto.rent.adapter.input.controller.handler.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static javax.validation.constraintvalidation.ValidationTarget.ANNOTATED_ELEMENT;

import com.felpeto.rent.adapter.input.controller.handler.annotation.validator.RegexConstraintValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraintvalidation.SupportedValidationTarget;

@Documented
@Constraint(validatedBy = {RegexConstraintValidator.class})
@Target({
    METHOD,
    FIELD,
    ANNOTATION_TYPE,
    CONSTRUCTOR,
    PARAMETER,
    TYPE_USE})
@Retention(RUNTIME)
@SupportedValidationTarget(ANNOTATED_ELEMENT)
@ReportAsSingleViolation
public @interface Regex {

  String exp();

  String message() default "";

  Class<? extends Payload>[] payload() default {};

  Class<?>[] groups() default {};
}
