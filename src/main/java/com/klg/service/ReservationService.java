package com.klg.service;


import com.klg.dto.ReservationDto;
import com.klg.exception.RentObjectNotFoundException;
import com.klg.exception.ReservationException;
import com.klg.exception.ReservationNotFoundException;
import com.klg.exception.TenantNotFoundException;
import com.klg.model.RentObject;
import com.klg.model.Reservation;
import com.klg.model.Tenant;
import com.klg.repository.RentObjectRepository;
import com.klg.repository.ReservationRepository;
import com.klg.repository.TenantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TenantRepository tenantRepository;
    private final RentObjectRepository rentObjectRepository;

    @Transactional
    public Reservation addReservation(ReservationDto reservationDto){

        Optional<Tenant> tenant = tenantRepository.findById(reservationDto.getTenantId());
        Optional<RentObject> rentObject = rentObjectRepository.findById(reservationDto.getRentObjectId());

        checkConstraints(reservationDto,tenant,rentObject);

        return reservationRepository.save(
                Reservation.builder()
                           .endDate(reservationDto.getEndDate())
                           .startDate(reservationDto.getStartDate())
                           .tenant(tenant.get())
                           .rentObject(rentObject.get())
                           .cost(countCost(reservationDto.getEndDate(),reservationDto.getStartDate(),rentObject.get()))
                           .build());
    }


    @Transactional
    public Reservation updateReservation(Long id, ReservationDto reservationDto) {

        Optional<Reservation> reservation = reservationRepository.findById(id);
        Optional<Tenant> tenant = tenantRepository.findById(reservationDto.getTenantId());
        Optional<RentObject> rentObject = rentObjectRepository.findById(reservationDto.getRentObjectId());

        if(reservation.isEmpty())
            throw new ReservationNotFoundException("Reservation with this id doesn't exist");
        checkConstraints(reservationDto,tenant,rentObject);

        reservation.get().setStartDate(reservationDto.getStartDate());
        reservation.get().setEndDate(reservationDto.getEndDate());
        reservation.get().setRentObject(rentObject.get());
        reservation.get().setTenant(tenant.get());
        reservation.get().setCost(countCost(reservationDto.getEndDate(),reservationDto.getStartDate(),rentObject.get()));

        return reservationRepository.save(reservation.get());

    }

    public List<Reservation> getReservations(){
        return reservationRepository.findAll();
    }

    private Boolean checkAvability(Date startDate, Date endDate, Long idObject){

        List<Reservation> reservations = reservationRepository.checkReservations(
                endDate,startDate,idObject);

        return reservations.isEmpty();

    }

    private Integer countCost(Date endDateSql, Date startDateSql, RentObject rentObject){

        long diffInMillies = endDateSql.getTime() - startDateSql.getTime();
        Integer diffInDays = Math.toIntExact(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS));

        return rentObject.getPriceForDay() * diffInDays;
    }

    private void checkConstraints(ReservationDto reservationDto, Optional<Tenant> tenant, Optional<RentObject> rentObject ){

        if(!checkAvability(reservationDto.getStartDate(),reservationDto.getEndDate(),reservationDto.getRentObjectId()))
            throw new ReservationException("Object already reserved on this date");
        if(tenant.isEmpty())
            throw new TenantNotFoundException("Tenant with this id doesn't exist");
        if(rentObject.isEmpty())
            throw new RentObjectNotFoundException("Object with this id doesn't exist");

    }

    public List<Reservation> getReservationsForObject(String objectName){
        return reservationRepository.findReservationsByRentObject_Name(objectName);
    }

    public List<Reservation> getReservationsForTenant(String tenantName) {
        return reservationRepository.findReservationsByTenant_Name(tenantName);
    }

}
