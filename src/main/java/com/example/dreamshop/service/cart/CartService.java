package com.example.dreamshop.service.cart;

import com.example.dreamshop.exceptions.ResourceNotFoundException;
import com.example.dreamshop.model.Cart;
import com.example.dreamshop.repository.CartItemRepository;
import com.example.dreamshop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {

        Cart cart = getCart(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return null;
    }
}
