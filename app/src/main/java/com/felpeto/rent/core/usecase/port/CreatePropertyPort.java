package com.felpeto.rent.core.usecase.port;

import com.felpeto.rent.core.domain.Property;

public interface CreatePropertyPort {

  Property create(final Property property);
}
