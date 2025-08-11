package com.mathildas.ecommerce.controller.admin;

import com.mathildas.ecommerce.dto.CouponDTO;
import com.mathildas.ecommerce.dto.ResponseBody;
import com.mathildas.ecommerce.exceptions.ValidationException;
import com.mathildas.ecommerce.services.admin.coupon.AdminCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/coupons")
public class AdminCouponController {

    @Autowired
    private AdminCouponService couponService;

    @PostMapping
    public ResponseEntity<ResponseBody<CouponDTO>> createCoupon(@RequestBody CouponDTO couponDTO) {
        try {
            CouponDTO createdCoupon = couponService.createCoupon(couponDTO);
            return ResponseEntity.ok(
                    ResponseBody.<CouponDTO>builder()
                            .statusCode(HttpStatus.CREATED.value())
                            .message("Coupon created successfully")
                            .data(createdCoupon)
                            .build()
            );
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(
                    ResponseBody.<CouponDTO>builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<CouponDTO>>> getAllCoupons() {
        List<CouponDTO> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(
                ResponseBody.<List<CouponDTO>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Coupons retrieved successfully")
                        .data(coupons)
                        .build()
        );
    }
}
