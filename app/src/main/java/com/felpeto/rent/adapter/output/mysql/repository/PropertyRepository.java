package com.felpeto.rent.adapter.output.mysql.repository;

import com.felpeto.rent.adapter.output.mysql.entity.PropertyEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PropertyRepository implements PanacheRepository<PropertyEntity> {

}
