package com.example.dreamshop.service.cart;

import com.example.dreamshop.exceptions.ResourceNotFoundException;
import com.example.dreamshop.model.Cart;
import com.example.dreamshop.model.CartItem;
import com.example.dreamshop.model.Product;
import com.example.dreamshop.repository.CartItemRepository;
import com.example.dreamshop.repository.CartRepository;
import com.example.dreamshop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
//        1. Get The Cart
//        2. Get the product
//        3. Check if the product already exist in the cart
//        4. If yes then increase the quantity with the requested quantity
//        5. If no then initiate a new CartItem entry

        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);

        CartItem cartItem = cart.getCartItem()
                .stream()
                .filter(item  -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItemFromAnyWhere(cartId, productId);
        cart.removeItem(itemToRemove);

        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);
        cart.getCartItem().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(cartItem -> {
                    cartItem.setQuantity(quantity);
                    cartItem.setUnitPrice(cartItem.getProduct().getPrice());
                    cartItem.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getCartItem()
                .stream().map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);

    }

    @Override
    public CartItem getCartItemFromAnyWhere(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return  cart.getCartItem()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
