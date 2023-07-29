package com.onrender.navkolodozvillya.cart;

import com.onrender.navkolodozvillya.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    private CartService underTest;
    @BeforeEach
    void setUp() {
        underTest = new CartService(cartRepository);
    }

    @Test
    public void shouldCreateCart(){
        // given
        var user = User.builder()
                .id(1L)
                .build();
        var cart = new Cart();
        given(cartRepository.save(any(Cart.class)))
                .willReturn(cart);
        // when
        var createdCart = underTest.createCart(user);
        // then
        assertThat(createdCart).isNotNull();

        var cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository, times(1)).save(cartArgumentCaptor.capture());
        var capturedCart = cartArgumentCaptor.getValue();
        assertThat(user).isEqualTo(capturedCart.getUser());
    }
}
