package com.onrender.navkolodozvillya.cart;


import com.onrender.navkolodozvillya.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "user")
public class Cart {
    @Id
    private Long id;
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

}
