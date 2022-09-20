package com.klg.controller;

import com.klg.dto.ReservationDto;
import com.klg.model.Reservation;
import com.klg.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping(path = "")
    private ResponseEntity<?> addNewService(@RequestBody ReservationDto reservationDto){
        return ResponseEntity.ok(reservationService.addReservation(reservationDto));
    }

    @GetMapping(path = "/objectName/{objectName}")
    public ResponseEntity<?> getReservationsForObject(@PathVariable("objectName") String objectName) {
        return ResponseEntity.ok(reservationService.getReservationsForObject(objectName));
    }

    @GetMapping(path = "/tenantName/{tenantName}")
    public ResponseEntity<?> getReservationsForTenant(@PathVariable("tenantName") String tenantName) {
        return ResponseEntity.ok(reservationService.getReservationsForTenant(tenantName));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateService (@PathVariable Long id,@RequestBody ReservationDto reservationDto) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservationDto));
    }

}
