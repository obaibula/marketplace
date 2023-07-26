package com.onrender.navkolodozvillya.favouriteoffering;

import com.onrender.navkolodozvillya.offering.Offering;
import com.onrender.navkolodozvillya.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favourite_offerings")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"user", "offering"})
public class FavouriteOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "offering_id")
    private Offering offering;
}
