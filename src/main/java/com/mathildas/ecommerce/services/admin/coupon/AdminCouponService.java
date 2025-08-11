package com.mathildas.ecommerce.services.admin.coupon;

import com.mathildas.ecommerce.dto.CouponDTO;

import java.util.List;

public interface AdminCouponService {

    CouponDTO createCoupon(CouponDTO coupon);
    boolean exitsByCode(String code);
    List<CouponDTO> getAllCoupons();
}
