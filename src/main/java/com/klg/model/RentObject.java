package com.klg.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "rent_objects")
public class RentObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rent_object", nullable = false)
    private Long idRentObject;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price_for_day", nullable = false)
    private Integer priceForDay;

    @Column(name = "size")
    private Integer size;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional=false)
    @JoinColumn(name = "id_owner", nullable=false)
    private Owner owner;

}
