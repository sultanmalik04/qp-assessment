package com.sultan.grocery_shop.controller;

import com.sultan.grocery_shop.dto.GroceryItemDTO;
import com.sultan.grocery_shop.dto.OrderDTO;
import com.sultan.grocery_shop.dto.OrderRequestDTO;
import com.sultan.grocery_shop.service.GroceryItemService;
import com.sultan.grocery_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final GroceryItemService groceryItemService;
    private final OrderService orderService;

    @GetMapping("/items")
    public ResponseEntity<List<GroceryItemDTO>> getAvailableItems() {
        return ResponseEntity.ok(groceryItemService.getAvailableItems());
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        return ResponseEntity.ok(orderService.getUserOrders());
    }
}