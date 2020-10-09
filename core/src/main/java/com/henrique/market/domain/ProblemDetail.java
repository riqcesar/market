/*
 * @(#)MessageException.java 1.0 26/05/20
 *
 * Copyright (c) 2020, PicPay S.A. All rights reserved.
 * PicPay S.A. proprietary/confidential. Use is subject to license terms.
 */

package com.henrique.market.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author Henrique Costa
 * @Version 1.0 26/05/20
 */
@Builder
@Getter
public class ProblemDetail {

    private Integer status;

    private String type;

    private String title;

    private String detail;

    private LocalDateTime createdAt;

}
