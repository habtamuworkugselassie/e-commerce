package com.kifiya.order.repository;

import com.kifiya.order.models.Order;
import com.kifiya.order.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByCustomerId(UUID customerId);

    List<Order> findByStatus(OrderStatus status);

    Optional<Order> findByIdempotencyKey(String idempotencyKey);
}

