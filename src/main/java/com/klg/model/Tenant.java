package com.klg.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tenant", nullable = false)
    private Long idTenant;

    @Column(name = "name", nullable = false)
    private String name;


}
