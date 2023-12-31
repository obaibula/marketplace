package com.onrender.navkolodozvillya.favouriteoffering;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavouriteOfferingRepository extends JpaRepository<FavouriteOffering, Long>{
    // find all to dto by principal email
    List<FavouriteOfferingResponse> findAllByUserId(Long userId);
    boolean existsByUserIdAndOfferingId(Long userId, Long offeringId);
}
