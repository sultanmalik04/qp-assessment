package com.sultan.grocery_shop.repository;

import com.sultan.grocery_shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}