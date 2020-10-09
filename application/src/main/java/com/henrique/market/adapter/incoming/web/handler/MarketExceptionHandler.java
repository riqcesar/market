/*
 * @(#)MarketExceptionHandler.java 1.0 26/05/20
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.henrique.market.adapter.incoming.web.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Henrique Costa
 * @Version 1.0 26/05/20
 */
@RestControllerAdvice
public class MarketExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, Object body, final HttpHeaders headers,
        final HttpStatus status, final WebRequest request) {

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

}
