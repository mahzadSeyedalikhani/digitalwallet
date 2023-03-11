package com.digipay.digitalwallet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ExceptionModel {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

}
