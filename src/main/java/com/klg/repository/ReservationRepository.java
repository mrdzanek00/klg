package com.klg.repository;

import com.klg.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findReservationsByTenant_Name(String name);
    List<Reservation> findReservationsByRentObject_Name(String name);

    @Query(value = "SELECT * FROM Reservations r " +
            "WHERE r.id_rent_object = :id_rent_object " +
            "AND r.start_date < :end_date AND r.end_date > :start_date ",
            nativeQuery = true)
    List<Reservation> checkReservations(
            @Param("end_date") Date endDate, @Param("start_date") Date startDate, @Param("id_rent_object") Long idRentObject);


}
