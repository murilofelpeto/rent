package com.felpeto.rent.core.usecase.port;

import com.felpeto.rent.core.domain.Page;
import com.felpeto.rent.core.domain.Property;
import java.util.List;
import java.util.UUID;

public interface GetPropertyPort {

  List<Property> getAllProperties(final Page page);

  Property getPropertyByUuid(final UUID uuid);

}
