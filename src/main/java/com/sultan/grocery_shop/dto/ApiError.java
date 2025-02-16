package com.sultan.grocery_shop.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
    private List<String> errors;
}