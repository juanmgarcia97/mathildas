package com.mathildas.ecommerce.entity;

import com.mathildas.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private double amount;

    private String address;

    private String description;

    private String payment;

    private OrderStatus status;

    @Column(name = "total_amount")
    private double totalAmount;

    private double discount;

    @Column(name = "tracking_id")
    private UUID trackingId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "cart_items")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItem> cartItems;

    public static Order createEmptyOrder(User user) {
        Order order = new Order();
        order.setAmount(0);
        order.setTotalAmount(0);
        order.setDiscount(0);
        order.setStatus(OrderStatus.Pending);
        order.setUser(user);
        return order;
    }
}
