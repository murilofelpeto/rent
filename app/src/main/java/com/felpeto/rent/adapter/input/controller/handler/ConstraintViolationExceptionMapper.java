package com.felpeto.rent.adapter.input.controller.handler;

import com.felpeto.rent.adapter.input.controller.handler.dto.ErrorDto;
import com.felpeto.rent.adapter.input.controller.handler.dto.Field;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements
    ExceptionMapper<ConstraintViolationException> {

  private static final String CONSTRAINT_MESSAGE = "The request is semantically incorrect or fails business validation";

  @Override
  public Response toResponse(final ConstraintViolationException exception) {
    final var fields = exception.getConstraintViolations()
        .stream()
        .map(this::toFields)
        .collect(Collectors.toList());

    final var timestamp = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
    final var errorResponse = new ErrorDto(CONSTRAINT_MESSAGE, 422, timestamp, fields);

    return Response.status(422).entity(errorResponse).build();
  }

  private Field toFields(final ConstraintViolation<?> constraintViolation) {
    return Field.builder()
        .name(constraintViolation.getPropertyPath().toString())
        .message(constraintViolation.getMessage())
        .build();
  }
}
