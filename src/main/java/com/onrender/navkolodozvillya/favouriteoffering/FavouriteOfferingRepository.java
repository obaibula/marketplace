package com.onrender.navkolodozvillya.favouriteoffering;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavouriteOfferingRepository extends JpaRepository<FavouriteOffering, Long> {
    // find all to dto by principal email
    List<FavouriteOfferingResponse> findAllByUserEmail(String userEmail);
    boolean existsByUserEmailAndOfferingId(String userEmail, Long offeringId);
}
