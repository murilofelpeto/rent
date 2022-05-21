package com.felpeto.rent.adapter.output.mysql;

import static com.felpeto.rent.adapter.output.mysql.mapper.PropertyMapper.toProperty;
import static java.text.MessageFormat.format;

import com.felpeto.rent.adapter.output.mysql.repository.PropertyRepository;
import com.felpeto.rent.core.domain.Page;
import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.usecase.port.GetPropertyPort;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetPropertyGateway implements GetPropertyPort {

  private static final String PROPERTY_NOT_FOUND = "Property with id {0} not found";
  private final PropertyRepository propertyRepository;

  public GetPropertyGateway(final PropertyRepository propertyRepository) {
    this.propertyRepository = propertyRepository;
  }

  @Override
  public List<Property> getAllProperties(final Page page) {
    final var properties = propertyRepository.findAll(page);
    return toProperty(properties);
  }

  @Override
  public Property getPropertyByUuid(final UUID uuid) {
    final var property = propertyRepository.findByUuid(uuid);

    if (property.isEmpty()) {
      throw new RuntimeException(format(PROPERTY_NOT_FOUND, uuid));
    }

    return toProperty(property.get());
  }
}
