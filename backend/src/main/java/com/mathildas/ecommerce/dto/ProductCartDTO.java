package com.mathildas.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCartDTO {

    private Long userId;
    private Long productId;
}
