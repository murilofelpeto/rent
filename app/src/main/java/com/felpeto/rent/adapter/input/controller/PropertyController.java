package com.felpeto.rent.adapter.input.controller;

import static com.felpeto.rent.adapter.input.controller.mapper.PageMapper.toPage;
import static com.felpeto.rent.adapter.input.controller.mapper.PropertyMapper.toProperty;
import static com.felpeto.rent.adapter.input.controller.mapper.PropertyResponseMapper.toPropertyResponse;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.felpeto.rent.adapter.input.controller.dto.request.PageDto;
import com.felpeto.rent.adapter.input.controller.dto.request.PropertyRequestDto;
import com.felpeto.rent.adapter.input.controller.dto.response.PropertyResponseDto;
import com.felpeto.rent.core.usecase.PropertyCreatorUseCase;
import com.felpeto.rent.core.usecase.PropertyGetterUseCase;
import com.felpeto.rent.core.usecase.PropertyUpdaterUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
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

  private final PropertyCreatorUseCase propertyCreatorUseCase;
  private final PropertyGetterUseCase propertyGetterUseCase;
  private final PropertyUpdaterUseCase propertyUpdaterUseCase;

  public PropertyController(
      final PropertyCreatorUseCase propertyCreatorUseCase,
      final PropertyGetterUseCase propertyGetterUseCase,
      final PropertyUpdaterUseCase propertyUpdaterUseCase) {
    this.propertyCreatorUseCase = propertyCreatorUseCase;
    this.propertyGetterUseCase = propertyGetterUseCase;
    this.propertyUpdaterUseCase = propertyUpdaterUseCase;
  }

  @POST
  @Produces(APPLICATION_JSON)
  @Consumes(APPLICATION_JSON)
  @Operation(
      summary = "Create a property",
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
    final var property = toProperty(request);

    final var response = toPropertyResponse(propertyCreatorUseCase.createProperty(property));

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
                  array = @ArraySchema(schema = @Schema(implementation = PropertyResponseDto.class)))),
          @ApiResponse(
              responseCode = "204",
              description = "empty list")
      })
  public Response getProperties(@NotNull @Valid @BeanParam PageDto pageDto) {
    final var page = toPage(pageDto);
    final var properties = propertyGetterUseCase.getProperties(page);

    if (properties.isEmpty()) {
      return Response.noContent().build();
    }

    final var response = toPropertyResponse(properties);
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
                  schema = @Schema(implementation = PropertyResponseDto.class))),
          @ApiResponse(
              responseCode = "404",
              description = "Property not found",
              content =
              @Content(
                  mediaType = APPLICATION_JSON,
                  schema = @Schema(implementation = PropertyResponseDto.class)))
      })
  public Response getPropertyById(
      @NotNull @Schema(format = "uuid") @PathParam("id") final String uuid) {

    final var property = propertyGetterUseCase.getPropertyByUuid(UUID.fromString(uuid));
    final var response = toPropertyResponse(property);

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
    final var property = toProperty(request);
    final var updatedProperty = propertyUpdaterUseCase
        .updateProperty(UUID.fromString(uuid), property);

    final var response = toPropertyResponse(updatedProperty);

    return Response.ok().entity(response).build();
  }
}
