package com.sultan.grocery_shop.repository;

import com.sultan.grocery_shop.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
}