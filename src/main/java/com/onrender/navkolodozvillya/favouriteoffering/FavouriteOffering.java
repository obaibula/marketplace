package com.onrender.navkolodozvillya.favouriteoffering;

import com.onrender.navkolodozvillya.offering.Offering;
import com.onrender.navkolodozvillya.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(updatable = false)
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "offering_id")
    private Offering offering;

    @PrePersist
    protected void onCreate() {
        setCreatedAt(LocalDateTime.now());
    }
}
