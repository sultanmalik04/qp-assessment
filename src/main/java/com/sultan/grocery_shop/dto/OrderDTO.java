package com.sultan.grocery_shop.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private List<GroceryItemDTO> items;
    private LocalDateTime orderDate;
    private Double totalAmount;
}