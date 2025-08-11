package com.mathildas.ecommerce.controller.customer;

import com.mathildas.ecommerce.dto.*;
import com.mathildas.ecommerce.dto.ResponseBody;
import com.mathildas.ecommerce.exceptions.ValidationException;
import com.mathildas.ecommerce.services.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<ResponseBody<?>> addToCart(@RequestBody ProductCartDTO productCartDTO) {
        return cartService.addToCart(productCartDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseBody<OrderDTO>> getCartByUserId(@PathVariable Long userId) {
        OrderDTO orderDTO = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(
                ResponseBody.<OrderDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Cart retrieved successfully")
                        .data(orderDTO)
                        .build()
        );
    }

    @PostMapping("/apply-coupon/{userId}/{couponCode}")
    public ResponseEntity<ResponseBody<OrderDTO>> applyCouponToCart(@PathVariable Long userId, @PathVariable String couponCode) {
        try {
            OrderDTO orderDTO = cartService.applyCouponToCart(userId, couponCode);
            return ResponseEntity.ok(
                    ResponseBody.<OrderDTO>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Coupon applied successfully")
                            .data(orderDTO)
                            .build()
            );
        } catch (ValidationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ResponseBody.<OrderDTO>builder()
                                    .message(e.getMessage())
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .data(null)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ResponseBody.<OrderDTO>builder()
                                    .message("An error occurred while applying the coupon")
                                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .data(null)
                                    .build()
                    );
        }
    }

    @PostMapping("/item-quantity")
    public ResponseEntity<ResponseBody<OrderDTO>> increaseProductQuantity(@RequestBody ProductCartQuantityDTO productCartQuantityDTO) {
        OrderDTO orderDTO = cartService.modifyProductQuantity(productCartQuantityDTO);
        return ResponseEntity.ok(
                ResponseBody.<OrderDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Item quantity increased successfully")
                        .data(orderDTO)
                        .build()
        );
    }

    @PostMapping("/place-order")
    public ResponseEntity<ResponseBody<OrderDTO>> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO) {
        OrderDTO orderDTO = cartService.placeOrder(placeOrderDTO);
        return ResponseEntity.ok(
                ResponseBody.<OrderDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Order placed successfully")
                        .data(orderDTO)
                        .build()
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseBody<OrderDTO>> emptyCart(@PathVariable Long userId) {
        OrderDTO orderDTO = cartService.emptyCart(userId);
        return ResponseEntity.ok(
                ResponseBody.<OrderDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Cart emptied successfully")
                        .data(orderDTO)
                        .build()
        );
    }
}
