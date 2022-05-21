package com.felpeto.rent.adapter.input.controller.handler;

import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import com.felpeto.rent.adapter.input.controller.handler.dto.ErrorDto;
import com.felpeto.rent.core.usecase.exception.EntityNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

  @Override
  public Response toResponse(final EntityNotFoundException exception) {
    final var timestamp = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
    final var errorResponse = new ErrorDto(
        exception.getMessage(),
        NOT_FOUND.getStatusCode(),
        timestamp);

    return Response.status(NOT_FOUND).entity(errorResponse).build();
  }
}
