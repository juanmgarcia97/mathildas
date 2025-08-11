package com.mathildas.ecommerce.dto;

import lombok.Data;

@Data
public class PlaceOrderDTO {
    private Long userId;
    private String address;
    private String description;
}
