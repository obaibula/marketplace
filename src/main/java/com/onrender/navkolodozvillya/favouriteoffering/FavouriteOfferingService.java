package com.onrender.navkolodozvillya.favouriteoffering;

import com.onrender.navkolodozvillya.exception.entity.offering.OfferingIsAlreadyInFavoritesException;
import com.onrender.navkolodozvillya.exception.entity.offering.OfferingNotFoundException;
import com.onrender.navkolodozvillya.exception.entity.user.UserNotFoundException;
import com.onrender.navkolodozvillya.offering.OfferingRepository;
import com.onrender.navkolodozvillya.user.User;
import com.onrender.navkolodozvillya.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteOfferingService {
    private final FavouriteOfferingRepository favouriteOfferingRepository;
    private final OfferingRepository offeringRepository;
    private final UserRepository userRepository;

    public List<FavouriteOfferingResponse> findAllBy(Principal principal) {
        String userEmail = principal.getName();
        return favouriteOfferingRepository.findAllByUserEmail(userEmail);
    }

    @Transactional
    public FavouriteOfferingResponse save(Long offeringId, Authentication authentication) {
        checkIfOfferingExists(offeringId);
        var user = (User)authentication.getPrincipal();
        var userId = user.getId();

        checkIfAlreadyInFavourites(userId, offeringId);

        var favourite = new FavouriteOffering();
        favourite.setUser(userRepository.getReferenceById(userId));
        favourite.setOffering(offeringRepository.getReferenceById(offeringId));
        var saved = favouriteOfferingRepository.save(favourite);

        return new FavouriteOfferingResponse(saved.getId(), userId, offeringId);
    }

    private void checkIfOfferingExists(Long offeringId) {
        var offeringExists = offeringRepository.existsById(offeringId);
        if(!offeringExists){
            throw new OfferingNotFoundException("Offering not found with id: " + offeringId);
        }
    }

    private void checkIfAlreadyInFavourites(Long userId, Long offeringId ) {
        var favoriteOfferingExists =
                favouriteOfferingRepository.existsByUserIdAndOfferingId(userId, offeringId);
        if (favoriteOfferingExists) {
            throw new OfferingIsAlreadyInFavoritesException("Offering is already in favourites.");
        }
    }
}
