package com.rattrapage.microserviceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileAppNotFoundException extends RuntimeException {

    public FileAppNotFoundException(String s) {
        super(s);
    }

}
