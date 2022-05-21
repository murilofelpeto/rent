package com.felpeto.rent.core.usecase;

import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.usecase.port.CreatePropertyPort;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PropertyCreatorUseCase {

  private final CreatePropertyPort createPropertyPort;

  public PropertyCreatorUseCase(
      final CreatePropertyPort createPropertyPort) {
    this.createPropertyPort = createPropertyPort;
  }

  public Property createProperty(final Property property) {
    return createPropertyPort.create(property);
  }
}
