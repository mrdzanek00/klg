package com.klg.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ReservationException extends RuntimeException{

    public ReservationException(String s) {
        super(s);
    }
}
