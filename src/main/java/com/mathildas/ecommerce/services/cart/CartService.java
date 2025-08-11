package com.mathildas.ecommerce.services.cart;

import com.mathildas.ecommerce.dto.*;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<ResponseBody<?>> addToCart(ProductCartDTO productCartDTO);
    OrderDTO getCartByUserId(Long userId);
    OrderDTO applyCouponToCart(Long userId, String couponCode);
    OrderDTO modifyProductQuantity(ProductCartQuantityDTO productCartQuantityDTO);
    OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO);
    OrderDTO emptyCart(Long userId);
}
