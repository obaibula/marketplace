package com.onrender.navkolodozvillya.user;


import com.onrender.navkolodozvillya.favouriteoffering.FavouriteOffering;
import com.onrender.navkolodozvillya.offering.Offering;
import com.onrender.navkolodozvillya.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // todo: consider creating entity of roles instead of this approach...
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Token> tokens;

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Offering> offerings;

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FavouriteOffering> favouriteOfferings;

    public void addOffering(Offering offering) {
        offerings.add(offering);
        offering.setUser(this);
    }

    public void removeOffering(Offering offering) {
        offerings.remove(offering);
        offering.setUser(null);
    }

    public void addFavouriteOffering(FavouriteOffering favouriteOffering) {
        favouriteOfferings.add(favouriteOffering);
        favouriteOffering.setUser(this);
    }

    public void removeFavouriteOffering(FavouriteOffering favouriteOffering) {
        favouriteOfferings.remove(favouriteOffering);
        favouriteOffering.setUser(null);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
