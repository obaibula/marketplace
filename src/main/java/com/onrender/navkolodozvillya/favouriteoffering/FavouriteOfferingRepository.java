package com.onrender.navkolodozvillya.favouriteoffering;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteOfferingRepository extends JpaRepository<FavouriteOffering, Long> {
    // find all to dto by principal email
    List<FavouriteOfferingResponse> findAllByUserEmail(String userEmail);
}
