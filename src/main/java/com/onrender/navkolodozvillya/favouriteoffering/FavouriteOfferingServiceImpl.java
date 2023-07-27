package com.onrender.navkolodozvillya.favouriteoffering;

import com.onrender.navkolodozvillya.exception.OfferingIsAlreadyInFavoritesException;
import com.onrender.navkolodozvillya.exception.OfferingNotFoundException;
import com.onrender.navkolodozvillya.exception.UserNotFoundException;
import com.onrender.navkolodozvillya.offering.OfferingRepository;
import com.onrender.navkolodozvillya.offering.OfferingService;
import com.onrender.navkolodozvillya.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FavouriteOfferingServiceImpl implements FavouriteOfferingService {
    private final FavouriteOfferingRepository favouriteOfferingRepository;
    private final UserRepository userRepository;
    private final OfferingRepository offeringRepository;
    private final FavouriteOfferingResponseMapper mapper;
    @Override
    public List<FavouriteOfferingResponse> findAllBy(Principal principal) {
        String userEmail = principal.getName();
        return favouriteOfferingRepository.findAllByUserEmail(userEmail);
    }

    /*@Override
    @Transactional
    // todo: refactor, it works, but overcomplicated(?). It should be in 1-2 queries.
    public FavouriteOfferingResponse save(Long offeringId, Principal principal) {
        var userEmail = principal.getName();
        // All offerings must be unique for every user in their favorites
        var favoriteOfferingExists =
                favouriteOfferingRepository.existsByUserEmailAndOfferingId(userEmail, offeringId);
        if (favoriteOfferingExists) {
            throw new OfferingIsAlreadyInFavoritesException("Offering is already in favorites.");
        }

        // If the offering is not in the user's favorites, add it.
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email - " + userEmail));
        var offering = offeringRepository.findById(offeringId)
                .orElseThrow(() -> new OfferingNotFoundException("Offering not found with id - " + offeringId));


        var favourite = new FavouriteOffering();
        favourite.setUser(user);
        favourite.setOffering(offering);

        var saved = favouriteOfferingRepository.save(favourite);

        return mapper.apply(saved);
    }*/

    @Transactional
    // todo: refactor, it works, but overcomplicated(?). It should be in 1-2 queries.
    public FavouriteOfferingResponse save(Long offeringId, Principal principal) {
        var userEmail = principal.getName();
        // All offerings must be unique for every user in their favorites
        /*var favoriteOfferingExists =
                favouriteOfferingRepository.existsByUserEmailAndOfferingId(userEmail, offeringId);
        if (favoriteOfferingExists) {
            throw new OfferingIsAlreadyInFavoritesException("Offering is already in favorites.");
        }*/


        var saved = favouriteOfferingRepository.customSave(userEmail, offeringId);

        return saved;
    }
}
