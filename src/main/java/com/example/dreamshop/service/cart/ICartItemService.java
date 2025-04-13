package com.example.dreamshop.service.cart;

import com.example.dreamshop.model.CartItem;

import java.util.List;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);


    CartItem getCartItemFromAnyWhere(Long cartId, Long productId);
}
