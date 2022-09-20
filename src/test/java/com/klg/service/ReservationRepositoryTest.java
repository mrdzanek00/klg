package com.klg.service;

import com.klg.dto.ReservationDto;
import com.klg.exception.RentObjectNotFoundException;
import com.klg.exception.ReservationException;
import com.klg.exception.ReservationNotFoundException;
import com.klg.exception.TenantNotFoundException;
import com.klg.repository.ReservationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest()
class ReservationRepositoryTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @AfterEach
    public void clear() {
            reservationRepository.deleteAll();
    }

    @Test
    public void shouldAddReservation() {

        List<ReservationDto> reservationDtoList = new ArrayList<>();

        reservationDtoList.add(ReservationDto.builder()
                .startDate(Date.valueOf("2022-10-01"))
                .endDate(Date.valueOf("2022-10-11"))
                .rentObjectId(1L)
                .tenantId(1L)
                .build());

        reservationDtoList.add(ReservationDto.builder()
                .startDate(Date.valueOf("2022-09-01"))
                .endDate(Date.valueOf("2022-09-11"))
                .rentObjectId(1L)
                .tenantId(1L)
                .build());

        reservationDtoList.add(ReservationDto.builder()
                .startDate(Date.valueOf("2022-11-01"))
                .endDate(Date.valueOf("2022-11-11"))
                .rentObjectId(1L)
                .tenantId(1L)
                .build());


        Assertions.assertEquals(reservationDtoList.get(0).getStartDate(),
                reservationRepository.findById(reservationService.addReservation(reservationDtoList.get(0)).getIdReservation()).get().getStartDate());
        Assertions.assertEquals(reservationDtoList.get(1).getStartDate(),
                reservationRepository.findById(reservationService.addReservation(reservationDtoList.get(1)).getIdReservation()).get().getStartDate());
        Assertions.assertEquals(reservationDtoList.get(2).getStartDate(),
                reservationRepository.findById(reservationService.addReservation(reservationDtoList.get(2)).getIdReservation()).get().getStartDate());

    }

    @Test
    public void shouldNotAddReservationBecauseOfDate() {

        reservationService.addReservation(ReservationDto.builder()
                .startDate(Date.valueOf("2022-01-03"))
                .endDate(Date.valueOf("2022-01-15"))
                .rentObjectId(1L)
                .tenantId(1L)
                .build());

        Assertions.assertThrows(ReservationException.class, () -> reservationService.addReservation(ReservationDto.builder()
                .startDate(Date.valueOf("2022-01-01"))
                .endDate(Date.valueOf("2022-01-11"))
                .rentObjectId(1L)
                .tenantId(1L)
                .build()));

        Assertions.assertThrows(ReservationException.class, () -> reservationService.addReservation(ReservationDto.builder()
                .startDate(Date.valueOf("2022-01-04"))
                .endDate(Date.valueOf("2022-01-11"))
                .rentObjectId(1L)
                .tenantId(1L)
                .build()));

        Assertions.assertThrows(ReservationException.class, () -> reservationService.addReservation(ReservationDto.builder()
                .startDate(Date.valueOf("2022-01-01"))
                .endDate(Date.valueOf("2022-01-17"))
                .rentObjectId(1L)
                .tenantId(1L)
                .build()));

        Assertions.assertThrows(ReservationException.class, () -> reservationService.addReservation(ReservationDto.builder()
                .startDate(Date.valueOf("2022-01-11"))
                .endDate(Date.valueOf("2022-01-17"))
                .rentObjectId(1L)
                .tenantId(1L)
                .build()));

    }

    @Test
    public void shouldNotAddReservationBecauseOfRentObject() {

        Assertions.assertThrows(RentObjectNotFoundException.class, () -> reservationService.addReservation(ReservationDto.builder()
                .startDate(Date.valueOf("2022-01-01"))
                .endDate(Date.valueOf("2022-01-11"))
                .rentObjectId(99L)
                .tenantId(1L)
                .build()));
    }

    @Test
    public void shouldNotAddReservationBecauseOfTenant() {

        Assertions.assertThrows(TenantNotFoundException.class, () -> reservationService.addReservation(ReservationDto.builder()
                .startDate(Date.valueOf("2022-01-01"))
                .endDate(Date.valueOf("2022-01-11"))
                .rentObjectId(1L)
                .tenantId(99L)
                .build()));
    }

    @Test
    public void shouldUpdateReservation() {

        Long id = reservationService.addReservation(ReservationDto.builder()
                .startDate(Date.valueOf("2022-01-03"))
                .endDate(Date.valueOf("2022-01-15"))
                .rentObjectId(1L)
                .tenantId(1L)
                .build()).getIdReservation();

        reservationService.updateReservation(id,ReservationDto.builder()
                .startDate(Date.valueOf("2022-10-01"))
                .endDate(Date.valueOf("2022-10-11"))
                .rentObjectId(1L)
                .tenantId(2L)
                .build());

        Assertions.assertEquals(2L,reservationRepository.findById(id).get().getTenant().getIdTenant());
        Assertions.assertEquals(Date.valueOf("2022-10-11"),reservationRepository.findById(id).get().getEndDate());
        Assertions.assertEquals(Date.valueOf("2022-10-01"),reservationRepository.findById(id).get().getStartDate());


    }

   @Test
    public void shouldNotUpdateReservationBecauseOfWrongReservationId() {

       Assertions.assertThrows(ReservationNotFoundException.class,() -> reservationService.updateReservation(10L,ReservationDto.builder()
               .startDate(Date.valueOf("2022-10-01"))
               .endDate(Date.valueOf("2022-10-11"))
               .rentObjectId(1L)
               .tenantId(2L)
               .build()));
    }


}