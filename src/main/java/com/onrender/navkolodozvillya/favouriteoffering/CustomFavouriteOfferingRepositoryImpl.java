package com.onrender.navkolodozvillya.favouriteoffering;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.query.Query;

public class CustomFavouriteOfferingRepositoryImpl implements CustomFavouriteOfferingRepository<FavouriteOfferingResponse> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FavouriteOfferingResponse customSave(String userEmail, Long offeringId) {
        return (FavouriteOfferingResponse)entityManager.createNativeQuery("""
                SELECT fo.id, u.id, o.id
                FROM favourite_offerings fo
                JOIN users u ON u.id = fo.user_id
                JOIN offerings o ON o.id = fo.offering_id
                WHERE u.email = :userEmail AND o.id = :offeringId
                """)
                .unwrap(Query.class)
                .setParameter("userEmail", userEmail)
                .setParameter("offeringId", offeringId)
                .getSingleResult();
    }
}
