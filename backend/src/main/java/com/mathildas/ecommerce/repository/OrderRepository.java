package com.mathildas.ecommerce.repository;

import com.mathildas.ecommerce.entity.Order;
import com.mathildas.ecommerce.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndStatus(Long userId, OrderStatus status);
}
