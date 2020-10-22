package com.henrique.market.controlleradvice;


import com.henrique.market.controlleradvice.dto.ErrorDTO;
import com.henrique.market.controlleradvice.dto.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@RestControllerAdvice
public class MarketControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        final List<FieldErrorDTO> fieldErrorDtos = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(FieldErrorDTO::from)
            .collect(toUnmodifiableList());

        return ErrorDTO.from(HttpStatus.BAD_REQUEST, "invalid arguments", fieldErrorDtos);
    }

}
