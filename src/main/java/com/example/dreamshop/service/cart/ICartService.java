package com.example.dreamshop.service.cart;

import com.example.dreamshop.model.Cart;
import com.example.dreamshop.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
