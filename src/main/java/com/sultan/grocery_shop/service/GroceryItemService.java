package com.sultan.grocery_shop.service;

import com.sultan.grocery_shop.dto.GroceryItemDTO;
import com.sultan.grocery_shop.exception.InsufficientInventoryException;
import com.sultan.grocery_shop.exception.ResourceNotFoundException;
import com.sultan.grocery_shop.model.GroceryItem;
import com.sultan.grocery_shop.repository.GroceryItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroceryItemService {

    private final GroceryItemRepository groceryItemRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public GroceryItemDTO addItem(GroceryItemDTO itemDTO) {
        GroceryItem item = modelMapper.map(itemDTO, GroceryItem.class);
        GroceryItem savedItem = groceryItemRepository.save(item);
        return modelMapper.map(savedItem, GroceryItemDTO.class);
    }

    public List<GroceryItemDTO> getAllItems() {
        return groceryItemRepository.findAll().stream()
                .map(item -> modelMapper.map(item, GroceryItemDTO.class))
                .collect(Collectors.toList());
    }

    public List<GroceryItemDTO> getAvailableItems() {
        return groceryItemRepository.findAll().stream()
                .filter(item -> item.getInventory() > 0)
                .map(item -> modelMapper.map(item, GroceryItemDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public GroceryItemDTO updateItem(Long id, GroceryItemDTO itemDTO) {
        GroceryItem existingItem = groceryItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grocery item not found"));

        modelMapper.map(itemDTO, existingItem);
        existingItem.setId(id);

        GroceryItem updatedItem = groceryItemRepository.save(existingItem);
        return modelMapper.map(updatedItem, GroceryItemDTO.class);
    }

    @Transactional
    public void deleteItem(Long id) {
        if (!groceryItemRepository.existsById(id)) {
            throw new EntityNotFoundException("Grocery item not found");
        }
        groceryItemRepository.deleteById(id);
    }

    public GroceryItemDTO getItemById(Long id) {
        GroceryItem item = groceryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grocery item not found with id: " + id));
        return modelMapper.map(item, GroceryItemDTO.class);
    }

    @Transactional
    public void updateInventory(Long id, Integer quantity) {
        GroceryItem item = groceryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grocery item not found with id: " + id));

        if (item.getInventory() < quantity) {
            throw new InsufficientInventoryException(
                    "Insufficient inventory for item: " + item.getName());
        }

        item.setInventory(item.getInventory() - quantity);
        groceryItemRepository.save(item);
    }

}