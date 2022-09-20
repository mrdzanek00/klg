package com.klg.model;


import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation", nullable = false)
    private Long idReservation;

    @ManyToOne(optional=false)
    @JoinColumn(name = "id_tenant", nullable=false)
    private Tenant tenant;

    @ManyToOne(optional=false)
    @JoinColumn(name = "id_rent_object", nullable=false)
    private RentObject rentObject;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "cost", nullable = false)
    private Integer cost;



}
