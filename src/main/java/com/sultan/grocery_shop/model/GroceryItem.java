package com.sultan.grocery_shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "grocery_items")
public class GroceryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Integer inventory;
}