package com.mathildas.ecommerce.services.admin.coupon;

import com.mathildas.ecommerce.dto.CouponDTO;
import com.mathildas.ecommerce.entity.Coupon;
import com.mathildas.ecommerce.exceptions.ValidationException;
import com.mathildas.ecommerce.mapper.CouponMapper;
import com.mathildas.ecommerce.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCouponServiceImpl implements AdminCouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public CouponDTO createCoupon(CouponDTO coupon) {
        if(couponRepository.existsByCode(coupon.getCode())) {
            throw new ValidationException("Coupon code already exists");
        }
        Coupon entity = couponMapper.toEntity(coupon);
        return couponMapper.toDTO(couponRepository.save(entity));
    }

    @Override
    public boolean exitsByCode(String code) {
        return couponRepository.existsByCode(code);
    }

    @Override
    public List<CouponDTO> getAllCoupons() {
         return couponMapper.toDTOs(couponRepository.findAll());
    }
}
