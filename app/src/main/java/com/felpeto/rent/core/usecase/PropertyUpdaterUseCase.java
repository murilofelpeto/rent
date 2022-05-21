package com.felpeto.rent.core.usecase;

import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.usecase.port.UpdatePropertyPort;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PropertyUpdaterUseCase {

  private final UpdatePropertyPort updatePropertyPort;


  public PropertyUpdaterUseCase(
      final UpdatePropertyPort updatePropertyPort) {
    this.updatePropertyPort = updatePropertyPort;
  }

  public Property updateProperty(final UUID uuid, final Property property) {
    return updatePropertyPort.updateProperty(uuid, property);
  }
}
