package com.example.dreamshop.dto;

import com.example.dreamshop.model.Cart;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    private List<OrderDto> orders;
    private CartDto cart;

}
