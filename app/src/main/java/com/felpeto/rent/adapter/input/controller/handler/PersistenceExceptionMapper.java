package com.felpeto.rent.adapter.input.controller.handler;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import com.felpeto.rent.adapter.input.controller.handler.dto.ErrorDto;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {

  @Override
  public Response toResponse(final PersistenceException exception) {
    final var timestamp = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
    final var errorResponse = new ErrorDto(
        exception.getMessage(),
        INTERNAL_SERVER_ERROR.getStatusCode(),
        timestamp);

    return Response.status(INTERNAL_SERVER_ERROR).entity(errorResponse).build();
  }
}
