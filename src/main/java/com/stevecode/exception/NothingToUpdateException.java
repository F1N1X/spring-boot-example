package com.stevecode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class NothingToUpdateException extends RuntimeException {
    public NothingToUpdateException(String message) {
        super(message);
    }
}
