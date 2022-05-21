package com.felpeto.rent.core.usecase.port;

import com.felpeto.rent.core.domain.Property;
import java.util.UUID;

public interface UpdatePropertyPort {

  Property updateProperty(final UUID uuid, final Property property);
}
