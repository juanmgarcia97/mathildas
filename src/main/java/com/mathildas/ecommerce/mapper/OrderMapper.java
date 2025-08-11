package com.mathildas.ecommerce.mapper;

import com.mathildas.ecommerce.dto.OrderDTO;
import com.mathildas.ecommerce.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CartItemsMapper.class, CouponMapper.class})
public interface OrderMapper {

    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "coupon.code", target = "couponCode")
    OrderDTO toDTO(Order order);

    @Mapping(source = "userName", target = "user.name")
    @Mapping(source = "couponCode", target = "coupon.code")
    Order toEntity(OrderDTO orderDTO);
}
