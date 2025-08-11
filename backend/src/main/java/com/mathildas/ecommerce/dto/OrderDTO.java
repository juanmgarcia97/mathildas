package com.mathildas.ecommerce.dto;

import com.mathildas.ecommerce.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDTO {
    private Long id;
    private Date date;
    private double amount;
    private String address;
    private String payment;
    private OrderStatus status;
    private double totalAmount;
    private double discount;
    private UUID trackingId;
    private String userName;
    private String couponCode;
    private List<CartItemDTO> cartItems;
}
