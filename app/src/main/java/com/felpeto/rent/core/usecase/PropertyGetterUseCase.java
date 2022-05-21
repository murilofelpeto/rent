package com.felpeto.rent.core.usecase;

import com.felpeto.rent.core.domain.Page;
import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.usecase.port.GetPropertyPort;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PropertyGetterUseCase {

  private final GetPropertyPort getPropertyPort;

  public PropertyGetterUseCase(
      final GetPropertyPort getPropertyPort) {
    this.getPropertyPort = getPropertyPort;
  }

  public List<Property> getProperties(final Page page) {

    return getPropertyPort.getAllProperties(page);
  }
}
