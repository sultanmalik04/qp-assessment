package com.sultan.grocery_shop.service;

import com.sultan.grocery_shop.dto.OrderDTO;
import com.sultan.grocery_shop.dto.OrderRequestDTO;
import com.sultan.grocery_shop.exception.InsufficientInventoryException;
import com.sultan.grocery_shop.exception.InvalidRequestException;
import com.sultan.grocery_shop.exception.ResourceNotFoundException;
import com.sultan.grocery_shop.model.GroceryItem;
import com.sultan.grocery_shop.model.Order;
import com.sultan.grocery_shop.model.User;
import com.sultan.grocery_shop.repository.GroceryItemRepository;
import com.sultan.grocery_shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final GroceryItemRepository groceryItemRepository;
    private final GroceryItemService groceryItemService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public OrderDTO createOrder(OrderRequestDTO orderRequest) {
        if (orderRequest.getItemIds() == null || orderRequest.getItemIds().isEmpty()) {
            throw new InvalidRequestException("Order must contain at least one item");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);

        List<GroceryItem> items = orderRequest.getItemIds().stream()
                .map(id -> groceryItemRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Grocery item not found with id: " + id)))
                .collect(Collectors.toList());

        // Calculate total and check inventory
        double totalAmount = 0;
        for (GroceryItem item : items) {
            if (item.getInventory() <= 0) {
                throw new InsufficientInventoryException(
                        "Item out of stock: " + item.getName());
            }
            totalAmount += item.getPrice();
        }

        // Update inventory
        items.forEach(item -> groceryItemService.updateInventory(item.getId(), 1));

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setItems(items);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public List<OrderDTO> getUserOrders() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);

        return orderRepository.findAll().stream()
                .filter(order -> order.getUser().getId().equals(user.getId()))
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }
}
