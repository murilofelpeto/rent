package com.felpeto.rent.adapter.output.mysql;

import static com.felpeto.rent.adapter.output.mysql.mapper.PropertyEntityMapper.toEntity;
import static com.felpeto.rent.adapter.output.mysql.mapper.PropertyMapper.toProperty;
import static java.text.MessageFormat.format;

import com.felpeto.rent.adapter.output.mysql.repository.PropertyRepository;
import com.felpeto.rent.core.domain.Property;
import com.felpeto.rent.core.usecase.exception.DuplicateEntityException;
import com.felpeto.rent.core.usecase.port.CreatePropertyPort;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;

@Slf4j
@ApplicationScoped
public class CreatePropertyGateway implements CreatePropertyPort {

  private static final String PERSISTED_PROPERTY = "PROPERTY {} PERSISTED";
  private static final String ADDRESS_CONSTRAINT_NAME = "property.uc_property_entity_address";
  private static final String UUID_CONSTRAINT_NAME = "property.uc_property_uuid";
  private static final String PROPERTY_EXISTS = "Property already exists for uuid {0}";


  private final PropertyRepository propertyRepository;

  public CreatePropertyGateway(final PropertyRepository propertyRepository) {
    this.propertyRepository = propertyRepository;
  }


  @Override
  @Transactional
  public Property create(final Property property) {
    final var entity = toEntity(property);
    try {
      propertyRepository.persistAndFlush(entity);
      log.debug(PERSISTED_PROPERTY, property);
      return toProperty(entity);
    } catch (PersistenceException exception) {
      if (exception.getCause() instanceof ConstraintViolationException) {
        final var cause = ((ConstraintViolationException) exception.getCause()).getConstraintName();
        if (ADDRESS_CONSTRAINT_NAME.equals(cause) || UUID_CONSTRAINT_NAME.equals(cause)) {
          log.error(format(PROPERTY_EXISTS, property.getUuid()));
          throw new DuplicateEntityException(format(PROPERTY_EXISTS, property.getUuid()));
        }
      }
      throw exception;
    }
  }
}
