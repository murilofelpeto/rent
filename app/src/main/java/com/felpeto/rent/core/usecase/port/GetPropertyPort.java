package com.felpeto.rent.core.usecase.port;

import com.felpeto.rent.core.domain.Page;
import com.felpeto.rent.core.domain.Property;
import java.util.List;

public interface GetPropertyPort {

  List<Property> getAllProperties(final Page page);

}
