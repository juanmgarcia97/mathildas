package com.mathildas.ecommerce.mapper;

import com.mathildas.ecommerce.dto.CouponDTO;
import com.mathildas.ecommerce.entity.Coupon;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    CouponDTO toDTO(Coupon coupon);
    Coupon toEntity(CouponDTO couponDTO);
    List<CouponDTO> toDTOs(List<Coupon> coupons);
}
