package com.company.vehicles.exception;

import com.company.vehicles.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}