package com.mathildas.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CouponDTO {

    private Long id;
    private String name;
    private String code;
    private double discount;
    private Date expirationDate;
}
