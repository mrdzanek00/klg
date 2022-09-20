CREATE TABLE owners (
                        id_owner INT AUTO_INCREMENT NOT NULL,
                        name VARCHAR (200) NOT NULL,
                        PRIMARY KEY (id_owner)
);

CREATE TABLE tenants (
                         id_tenant INT AUTO_INCREMENT NOT NULL,
                         name VARCHAR (200) NOT NULL,
                         PRIMARY KEY (id_tenant)
);

CREATE TABLE rent_objects (
                              id_rent_object INT AUTO_INCREMENT NOT NULL,
                              name VARCHAR (200) NOT NULL,
                              id_owner INT,
                              price_for_day INT NOT NULL,
                              size INT,
                              description VARCHAR(200),
                              PRIMARY KEY (id_rent_object),
                              foreign key (id_owner) references owners(id_owner)
);

CREATE TABLE reservations (
                              id_reservation INT AUTO_INCREMENT NOT NULL,
                              start_date DATE NOT NULL,
                              end_date DATE NOT NULL,
                              id_tenant INT ,
                              id_rent_object INT,
                              cost INT,
                              PRIMARY KEY (id_reservation),
                              foreign key (id_tenant) references tenants(id_tenant),
                              foreign key (id_rent_object) references rent_objects(id_rent_object)


);



CREATE INDEX object_Name_Index ON rent_objects(name);

CREATE INDEX owner_Name_Index ON owners(name);

