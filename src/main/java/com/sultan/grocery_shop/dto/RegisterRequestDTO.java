package com.sultan.grocery_shop.dto;

import com.sultan.grocery_shop.model.Role;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private Role role;
}