package com.klg.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentObjectNotFoundException extends RuntimeException{

    public RentObjectNotFoundException(String s) {
        super(s);
    }

}
