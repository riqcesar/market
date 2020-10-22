package com.henrique.market.controlleradvice.dto;

import lombok.AllArgsConstructor;
import org.springframework.validation.FieldError;

@AllArgsConstructor
public class FieldErrorDTO {

    private String field;

    private String message;

    public static FieldErrorDTO from(FieldError fieldError) {
        return new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage());
    }

}
