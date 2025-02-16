package com.sultan.grocery_shop.controller;

import com.sultan.grocery_shop.dto.GroceryItemDTO;
import com.sultan.grocery_shop.service.GroceryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final GroceryItemService groceryItemService;

    @PostMapping("/items")
    public ResponseEntity<GroceryItemDTO> addItem(@RequestBody GroceryItemDTO item) {
        return ResponseEntity.ok(groceryItemService.addItem(item));
    }

    @GetMapping("/items")
    public ResponseEntity<List<GroceryItemDTO>> getAllItems() {
        return ResponseEntity.ok(groceryItemService.getAllItems());
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<GroceryItemDTO> updateItem(@PathVariable Long id, @RequestBody GroceryItemDTO item) {
        return ResponseEntity.ok(groceryItemService.updateItem(id, item));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        groceryItemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }
}