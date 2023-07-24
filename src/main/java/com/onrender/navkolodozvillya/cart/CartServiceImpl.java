package com.onrender.navkolodozvillya.cart;

import com.onrender.navkolodozvillya.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    @Override
    public Cart createCart(User user) {
        var cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}
