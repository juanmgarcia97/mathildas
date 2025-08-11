package com.mathildas.ecommerce.mapper;

import com.mathildas.ecommerce.dto.CartItemDTO;
import com.mathildas.ecommerce.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, UserMapper.class, OrderMapper.class})
public interface CartItemsMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.img", target = "productImage")
    CartItemDTO toDTO(CartItem cartItem);

    CartItem toEntity(CartItemDTO cartItemDTO);
}
