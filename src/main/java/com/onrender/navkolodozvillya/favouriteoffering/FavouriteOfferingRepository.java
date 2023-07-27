package com.onrender.navkolodozvillya.favouriteoffering;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavouriteOfferingRepository extends JpaRepository<FavouriteOffering, Long>{
    // find all to dto by principal email
    List<FavouriteOfferingResponse> findAllByUserEmail(String userEmail);

    boolean existsByUserEmailAndOfferingId(String userEmail, Long offeringId);
    @Modifying
    @Query(value = """
            INSERT INTO favourite_offerings(user_id, offering_id)
            VALUES(
                (SELECT u.id FROM users u WHERE u.email = ?1),
                ?2
            )
            """, nativeQuery = true)
    void saveByUserEmailAndOfferingIdUsingNativeQuery(String userEmail, Long offeringId);

    FavouriteOfferingResponse findByUserEmailAndOfferingId(String userEmail, Long offeringId);
}
