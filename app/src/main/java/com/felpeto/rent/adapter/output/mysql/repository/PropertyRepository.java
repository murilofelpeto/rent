package com.felpeto.rent.adapter.output.mysql.repository;

import static com.felpeto.rent.core.domain.vo.SortMode.ASC;

import com.felpeto.rent.adapter.output.mysql.entity.PropertyEntity;
import com.felpeto.rent.core.domain.Page;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PropertyRepository implements PanacheRepository<PropertyEntity> {

  public List<PropertyEntity> findAll(final Page page) {
    final Sort sort = getSort(page);
    return findAll(sort)
        .page(page.getOffset(), page.getLimit())
        .list();
  }

  public Optional<PropertyEntity> findByUuid(final UUID uuid) {
    return find("uuid", uuid)
        .firstResultOptional();
  }

  private Sort getSort(final Page page) {
    final var direction =
        (ASC.equals(page.getSortMode()))
            ? Direction.Ascending : Direction.Descending;

    return Sort.by(page.getSort(), direction);
  }
}
