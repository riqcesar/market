package com.henrique.market.controlleradvice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Builder
public class ErrorDTO {

    private int code;

    private String error;

    private String message;

    private List<FieldErrorDTO> fieldErrors;

    private LocalDateTime timestamp;

    public static ErrorDTO from(final HttpStatus status, final String message, final List<FieldErrorDTO> fieldErrors) {
        return ErrorDTO.builder()
            .code(status.value())
            .error(status.getReasonPhrase())
            .message(message)
            .fieldErrors(fieldErrors)
            .timestamp(LocalDateTime.now())
            .build();
    }

    public static ErrorDTO from(final HttpStatus status, final String message) {
        return from(status, message, null);
    }

}
