package com.mathildas.ecommerce.services.cart;

import com.mathildas.ecommerce.dto.*;
import com.mathildas.ecommerce.entity.*;
import com.mathildas.ecommerce.enums.OrderStatus;
import com.mathildas.ecommerce.exceptions.ValidationException;
import com.mathildas.ecommerce.mapper.OrderMapper;
import com.mathildas.ecommerce.repository.*;
import com.mathildas.ecommerce.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public ResponseEntity<ResponseBody<?>> addToCart(ProductCartDTO productCartDTO) {
        Order activeOrder = orderRepository.findByUserIdAndStatus(
                productCartDTO.getUserId(),
                OrderStatus.Pending
        );
        Optional<CartItem> optionalCartItems = cartItemsRepository.findByProductIdAndUserIdAndOrderId(
                productCartDTO.getProductId(),
                productCartDTO.getUserId(),
                activeOrder.getId()
        );

        if(optionalCartItems.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            ResponseBody.builder()
                                    .message("Product already exists in cart")
                                    .statusCode(HttpStatus.CONFLICT.value())
                                    .data(null)
                                    .build()
                    );
        } else {
            Optional<Product> optionalProduct = productRepository.findById(productCartDTO.getProductId());
            Optional<User> optionalUser = userRepository.findById(productCartDTO.getUserId());

            if(optionalProduct.isPresent() && optionalUser.isPresent()) {
                Product product = optionalProduct.get();
                User user = optionalUser.get();

                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setPrice(product.getPrice());
                cartItem.setUser(user);
                cartItem.setOrder(activeOrder);
                cartItem.setQuantity(Constants.MIN_PRODUCT_QUANTITY);

                CartItem savedCart = cartItemsRepository.save(cartItem);

                activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());
                activeOrder.getCartItems().add(cartItem);

                orderRepository.save(activeOrder);

                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(
                                ResponseBody.builder()
                                        .message("Product added to cart successfully")
                                        .statusCode(HttpStatus.CREATED.value())
                                        .data(savedCart)
                                        .build()
                        );
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(
                                ResponseBody.builder()
                                        .message("Product or User not found")
                                        .statusCode(HttpStatus.NOT_FOUND.value())
                                        .data(null)
                                        .build()
                        );
            }
        }
    }

    @Override
    public OrderDTO getCartByUserId(Long userId) {
        Order order = orderRepository.findByUserIdAndStatus(userId, OrderStatus.Pending);
        return orderMapper.toDTO(order);
    }

    @Override
    public OrderDTO applyCouponToCart(Long userId, String couponCode) {
        Order order = orderRepository.findByUserIdAndStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(couponCode).orElseThrow(() -> new ValidationException("Coupon not found"));

        if(isCouponExpired(coupon)) {
            throw new ValidationException("Coupon is expired");
        }

        if(order.getDiscount() > 0) {
            throw new ValidationException("Coupon already applied");
        }

        applyCouponDiscount(order);
        order.setCoupon(coupon);

        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    private boolean isCouponExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();
        return expirationDate != null && expirationDate.before(currentDate);
    }

    @Override
    public OrderDTO modifyProductQuantity(ProductCartQuantityDTO productCartQuantityDTO) {
        Long userId = productCartQuantityDTO.getUserId();
        Long productId = productCartQuantityDTO.getProductId();
        Order order = orderRepository.findByUserIdAndStatus(userId, OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<CartItem> optionalCartItem = cartItemsRepository.findByProductIdAndUserIdAndOrderId(productId, userId, order.getId());

        if(optionalProduct.isPresent() && optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            order.setAmount(order.getAmount() + product.getPrice() * productCartQuantityDTO.getQuantity());
            order.setTotalAmount(order.getTotalAmount() + product.getPrice() * productCartQuantityDTO.getQuantity());

            cartItem.setQuantity(cartItem.getQuantity() + productCartQuantityDTO.getQuantity());

            if(order.getCoupon() != null) {
                applyCouponDiscount(order);
            }

            orderRepository.save(order);
            cartItemsRepository.save(cartItem);
        } else {
            throw new ValidationException("Product quantity cannot be modified because product is not in cart or does not exist");
        }

        return orderMapper.toDTO(order);
    }

    private void applyCouponDiscount(Order order) {
        double orderDiscount = order.getTotalAmount() * order.getCoupon().getDiscount() / 100;
        double newOrderAmount = order.getTotalAmount() - orderDiscount;

        order.setAmount(newOrderAmount);
        order.setDiscount(orderDiscount);
    }

    @Override
    public OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO) {
        Order activeOrder = orderRepository.findByUserIdAndStatus(placeOrderDTO.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepository.findById(placeOrderDTO.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            activeOrder.setDescription(placeOrderDTO.getDescription());
            activeOrder.setAddress(placeOrderDTO.getAddress());
            activeOrder.setStatus(OrderStatus.Placed);
            activeOrder.setDate(new Date());
            activeOrder.setTrackingId(UUID.randomUUID());

            Order newOrder = Order.createEmptyOrder(user);

            orderRepository.save(activeOrder);
            orderRepository.save(newOrder);

            return orderMapper.toDTO(activeOrder);
        } else {
            throw new ValidationException("User not found");
        }
    }

    public OrderDTO emptyCart(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Order newOrder = Order.createEmptyOrder(user);

            orderRepository.save(newOrder);

            return orderMapper.toDTO(newOrder);
        } else {
            throw new ValidationException("User not found");
        }
    }
}
