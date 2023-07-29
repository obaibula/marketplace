package com.onrender.navkolodozvillya.cart;

import com.onrender.navkolodozvillya.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    public Cart createCart(User user) {
        var cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}
