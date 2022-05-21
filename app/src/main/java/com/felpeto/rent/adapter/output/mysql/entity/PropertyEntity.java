package com.felpeto.rent.adapter.output.mysql.entity;

import com.felpeto.rent.core.domain.vo.PropertyKind;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Entity
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "property",
    uniqueConstraints = {@UniqueConstraint(
        name = "uc_property_entity_address",
        columnNames = {
            "country",
            "state",
            "city",
            "zipcode",
            "street_name",
            "house_number",
            "complement"})})
public class PropertyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(columnDefinition = "BINARY(16)", nullable = false, unique = true)
  private UUID uuid;

  @Enumerated(EnumType.STRING)
  @Column(name = "property_kind", nullable = false)
  private PropertyKind propertyKind;

  @Column(name = "country", nullable = false, length = 100)
  private String country;

  @Column(name = "state", nullable = false, length = 2)
  private String state;

  @Column(name = "city", nullable = false, length = 100)
  private String city;

  @Column(name = "zipcode", nullable = false, length = 50)
  private String zipcode;

  @Column(name = "street_name", nullable = false)
  private String streetName;

  @Column(name = "house_number", nullable = false, length = 6)
  private Integer houseNumber;

  @Column(name = "complement", length = 100)
  private String complement;

  @Column(name = "tenant", nullable = false)
  private String tenant;

  @PrePersist
  public void prePersist() {
    this.uuid = UUID.randomUUID();
  }

}