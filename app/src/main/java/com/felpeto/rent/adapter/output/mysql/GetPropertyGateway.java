package com.felpeto.rent.adapter.output.mysql;

import static com.felpeto.rent.adapter.output.mysql.mapper.PropertyMapper.toProperty;

import com.felpeto.rent.adapter.output.mysql.repository.PropertyRepository;
import com.felpeto.rent.core.domain.Page;
import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.usecase.port.GetPropertyPort;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetPropertyGateway implements GetPropertyPort {

  private final PropertyRepository propertyRepository;

  public GetPropertyGateway(final PropertyRepository propertyRepository) {
    this.propertyRepository = propertyRepository;
  }

  @Override
  public List<Property> getAllProperties(final Page page) {
    final var properties = propertyRepository.findAll(page);
    return toProperty(properties);
  }
}
