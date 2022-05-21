package com.felpeto.rent.adapter.output.mysql;

import static com.felpeto.rent.adapter.output.mysql.mapper.PropertyMapper.toProperty;
import static java.text.MessageFormat.format;

import com.felpeto.rent.adapter.output.mysql.entity.PropertyEntity;
import com.felpeto.rent.adapter.output.mysql.repository.PropertyRepository;
import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.usecase.port.UpdatePropertyPort;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class UpdatePropertyGateway implements UpdatePropertyPort {

  private static final String PROPERTY_NOT_FOUND = "Property with id {0} not found";
  private static final String UPDATE_ERROR = "Error to update property";
  private static final String UPDATE_QUERY = "zipcode = ?1,"
      + "property_kind = ?2,"
      + "country = ?3,"
      + "state = ?4,"
      + "city = ?5,"
      + "street_name = ?6,"
      + "complement = ?7 "
      + "where uuid = ?8";
  private final PropertyRepository propertyRepository;

  public UpdatePropertyGateway(
      final PropertyRepository propertyRepository) {
    this.propertyRepository = propertyRepository;
  }

  @Override
  @Transactional
  public Property updateProperty(final UUID uuid, final Property property) {
    final var propertyEntity = propertyRepository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException(format(PROPERTY_NOT_FOUND, uuid)));

    final var result = propertyRepository.update(UPDATE_QUERY,
        property.getAddress().getZipCode().getValue(),
        property.getPropertyKind().name(),
        property.getAddress().getCountry().getValue(),
        property.getAddress().getState().getValue(),
        property.getAddress().getCity().getValue(),
        property.getAddress().getStreetName().getValue(),
        property.getAddress().getComplement(),
        uuid);

    if (result != 1) {
      throw new RuntimeException(UPDATE_ERROR);
    }

    propertyRepository.getEntityManager().refresh(propertyEntity);
    return toProperty(propertyEntity);
  }
}
