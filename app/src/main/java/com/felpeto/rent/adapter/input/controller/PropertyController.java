package com.felpeto.rent.adapter.input.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.felpeto.rent.adapter.input.controller.dto.request.PropertyRequestDto;
import com.felpeto.rent.adapter.input.controller.dto.response.PropertyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/v1/properties")
public class PropertyController {

  @POST
  @Produces(APPLICATION_JSON)
  @Consumes(APPLICATION_JSON)
  @Operation(
      summary = "Get all houses",
      requestBody =
      @RequestBody(
          required = true,
          content = @Content(
              schema = @Schema(implementation = PropertyRequestDto.class))),
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Return a created property",
              content =
              @Content(
                  mediaType = APPLICATION_JSON,
                  array = @ArraySchema(schema = @Schema(implementation = PropertyResponseDto.class))))
      })
  public Response createProperty(final PropertyRequestDto request) {
    log.info("creating property {}", request);
    final var response = PropertyResponseDto.builder()
        .city(request.getCity())
        .complement(request.getComplement())
        .country(request.getCountry())
        .id(UUID.randomUUID())
        .propertyKind(request.getPropertyKind())
        .number(request.getNumber())
        .state(request.getState())
        .streetName(request.getStreetName())
        .zipCode(request.getZipCode())
        .build();

    return Response.status(Status.CREATED).entity(response).build();
  }

  @GET
  @Produces(APPLICATION_JSON)
  @Consumes(APPLICATION_JSON)
  @Operation(
      summary = "Get all properties",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Return a list of properties",
              content =
              @Content(
                  mediaType = APPLICATION_JSON,
                  array = @ArraySchema(schema = @Schema(implementation = PropertyResponseDto.class))))
      })
  public Response getProperties() {
    final var response = List.of(PropertyResponseDto.builder()
        .city("São Paulo")
        .complement("N/A")
        .country("Brasil")
        .id(UUID.randomUUID())
        .propertyKind("house")
        .number(10)
        .state("São Paulo")
        .streetName("Rua da silva")
        .zipCode("02560-115")
        .build());

    return Response.ok().entity(response).build();
  }

  @GET
  @Path("/{id}")
  @Produces(APPLICATION_JSON)
  @Consumes(APPLICATION_JSON)
  @Operation(
      summary = "Get property by it's id",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Return a property",
              content =
              @Content(
                  mediaType = APPLICATION_JSON,
                  schema = @Schema(implementation = PropertyResponseDto.class)))
      })
  public Response getPropertyById(
      @NotNull @Schema(format = "uuid") @PathParam("id") final String uuid) {

    final var response = PropertyResponseDto.builder()
        .city("São Paulo")
        .complement("N/A")
        .country("Brasil")
        .id(UUID.fromString(uuid))
        .propertyKind("house")
        .number(10)
        .state("São Paulo")
        .streetName("Rua da silva")
        .zipCode("02560-115")
        .build();

    return Response.ok().entity(response).build();
  }

  @PUT
  @Path("/{id}")
  @Produces(APPLICATION_JSON)
  @Consumes(APPLICATION_JSON)
  @Operation(
      summary = "Update property by it's id",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Update a property",
              content =
              @Content(
                  mediaType = APPLICATION_JSON,
                  schema = @Schema(implementation = PropertyResponseDto.class)))
      })
  public Response updateProperty(
      @NotNull @Schema(format = "uuid") @PathParam("id") final String uuid,
      final PropertyRequestDto request) {
    final var response = PropertyResponseDto.builder()
        .city(request.getCity())
        .complement(request.getComplement())
        .country(request.getCountry())
        .id(UUID.fromString(uuid))
        .propertyKind(request.getPropertyKind())
        .number(request.getNumber())
        .state(request.getState())
        .streetName(request.getStreetName())
        .zipCode(request.getZipCode())
        .build();

    return Response.ok().entity(response).build();
  }
}
