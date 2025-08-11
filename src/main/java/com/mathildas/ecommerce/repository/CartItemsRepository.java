package com.mathildas.ecommerce.repository;

import com.mathildas.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductIdAndUserIdAndOrderId(Long productId, Long userId, Long orderId);
}
