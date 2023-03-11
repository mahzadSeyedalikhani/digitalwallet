package com.digipay.digitalwallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateWalletException.class)
    public ResponseEntity<Object> handleDuplicateWalletException(DuplicateWalletException duplicateWalletException){
        ExceptionModel payloadInformation = new ExceptionModel(duplicateWalletException.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(payloadInformation, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> handleInsufficientException(InsufficientBalanceException insufficientBalanceException){
        ExceptionModel payloadInformation = new ExceptionModel(insufficientBalanceException.getMessage(),
                HttpStatus.PAYMENT_REQUIRED,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(payloadInformation, HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(WalletAlreadyActiveException.class)
    public ResponseEntity<Object> handleWalletAlreadyActiveException(WalletAlreadyActiveException
                                                                             walletAlreadyActiveException){
        ExceptionModel payloadInformation = new ExceptionModel(walletAlreadyActiveException.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(payloadInformation, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WalletInactiveException.class)
    public ResponseEntity<Object> handleWalletAlreadyInactiveException(WalletInactiveException
                                                                               walletInactiveException){
        ExceptionModel payloadInformation = new ExceptionModel(walletInactiveException.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(payloadInformation, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Object> handleWalletNotFoundException(WalletNotFoundException
                                                                        walletNotFoundException){
        ExceptionModel payloadInformation = new ExceptionModel(walletNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(payloadInformation, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WalletTransactionNotFoundException.class)
    public ResponseEntity<Object> handleWalletTransactionNotFoundException(WalletTransactionNotFoundException
                                                                        walletTransactionNotFoundException){
        ExceptionModel payloadInformation = new ExceptionModel(walletTransactionNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(payloadInformation, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<Object> handleUserNotExistException(UserNotExistException
                                                                                   userNotExistException){
        ExceptionModel payloadInformation = new ExceptionModel(userNotExistException.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(payloadInformation, HttpStatus.NOT_FOUND);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handelValidationError(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> errorMap = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
}
