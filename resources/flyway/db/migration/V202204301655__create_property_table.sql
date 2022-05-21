CREATE TABLE property (
  id BIGINT AUTO_INCREMENT NOT NULL,
   uuid BINARY(16) NOT NULL,
   property_kind VARCHAR(255) NOT NULL,
   country VARCHAR(100) NOT NULL,
   state VARCHAR(2) NOT NULL,
   city VARCHAR(100) NOT NULL,
   zipcode VARCHAR(50) NOT NULL,
   street_name VARCHAR(255) NOT NULL,
   house_number INT NOT NULL,
   complement VARCHAR(100) NULL,
   tenant VARCHAR(255) NOT NULL,
   CONSTRAINT pk_property PRIMARY KEY (id)
);

ALTER TABLE property ADD CONSTRAINT uc_property_entity_address UNIQUE (country, state, city, zipcode, street_name, house_number, complement);

ALTER TABLE property ADD CONSTRAINT uc_property_uuid UNIQUE (uuid);