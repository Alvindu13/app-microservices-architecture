package com.rattrapage.microserviceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String s) {
        super(s);
    }
}
