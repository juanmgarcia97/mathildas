package com.mathildas.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private double price;
    private int quantity;
    private Long productId;
    private Long userId;
    private Long orderId;
    private String productName;
    private byte[] productImage;
}
