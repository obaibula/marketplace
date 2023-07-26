package com.onrender.navkolodozvillya.offering;

import com.onrender.navkolodozvillya.favouriteoffering.FavouriteOffering;
import com.onrender.navkolodozvillya.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "offerings")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "user")
@AllArgsConstructor
@Builder
public class Offering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferingCategory category;

    @Column(updatable = false)
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createdAt;

    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "offering",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<FavouriteOffering> favouriteOfferings;

    public void addFavouriteOffering(FavouriteOffering favouriteOffering) {
        favouriteOfferings.add(favouriteOffering);
        favouriteOffering.setOffering(this);
    }

    public void removeFavouriteOffering(FavouriteOffering favouriteOffering) {
        favouriteOfferings.remove(favouriteOffering);
        favouriteOffering.setOffering(null);
    }

    @PrePersist
    protected void onCreate() {
        var now = LocalDateTime.now();
        setCreatedAt(now);
        setUpdatedAt(now);
    }

    @PreUpdate
    protected void onUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

}
