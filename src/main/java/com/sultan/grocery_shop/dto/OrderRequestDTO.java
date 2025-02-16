package com.sultan.grocery_shop.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDTO {
    private List<Long> itemIds;
}
