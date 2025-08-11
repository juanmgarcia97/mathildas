package com.mathildas.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCartQuantityDTO {
    private Long userId;
    private Long productId;
    private int quantity;
}
