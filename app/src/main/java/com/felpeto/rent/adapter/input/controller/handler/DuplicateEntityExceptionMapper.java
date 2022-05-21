package com.felpeto.rent.adapter.input.controller.handler;

import static javax.ws.rs.core.Response.Status.CONFLICT;

import com.felpeto.rent.adapter.input.controller.handler.dto.ErrorDto;
import com.felpeto.rent.core.usecase.exception.DuplicateEntityException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DuplicateEntityExceptionMapper implements ExceptionMapper<DuplicateEntityException> {

  @Override
  public Response toResponse(final DuplicateEntityException exception) {
    final var timestamp = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
    final var errorResponse = new ErrorDto(
        exception.getMessage(),
        CONFLICT.getStatusCode(),
        timestamp);

    return Response.status(CONFLICT).entity(errorResponse).build();
  }
}
