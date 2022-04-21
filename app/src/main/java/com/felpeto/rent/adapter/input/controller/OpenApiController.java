package com.felpeto.rent.adapter.input.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import java.io.InputStream;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Named
@Path("/v1/openapi")
@Produces(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(
    info = @Info(
        title = "Rent API",
        version = "1.0.0",
        description = "Manage your rental properties",
        contact = @Contact(url = "https://github.com/murilofelpeto", name = "Murilo Felpeto", email = "murilofelpeto@confidential.com")
    )
)
public class OpenApiController {

  private static final String OPENAPI_FILE_PATH = "openapi.json";

  @GET
  @Operation(hidden = true)
  public InputStream openApi() {
    return OpenApiController.class.getResourceAsStream(OPENAPI_FILE_PATH);
  }
}