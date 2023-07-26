package com.onrender.navkolodozvillya.favouriteoffering;

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

@Service
@RequiredArgsConstructor
public class FavouriteOfferingServiceImpl implements FavouriteOfferingService {
    private final FavouriteOfferingRepository favouriteOfferingRepository;
    private final UserRepository userRepository;
    private final OfferingRepository offeringRepository;
    @Override
    public List<FavouriteOfferingResponse> findAllBy(Principal principal) {
        String userEmail = principal.getName();
        return favouriteOfferingRepository.findAllByUserEmail(userEmail);
    }

    @Override
    @Transactional
    // todo: refactor, it works, but overcomplicated(?)
    public FavouriteOfferingResponse save(Long offeringId, Principal principal) {
        var userEmail = principal.getName();

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email - " + userEmail));
        var offering = offeringRepository.findById(offeringId)
                .orElseThrow(() -> new OfferingNotFoundException("Offering not found with id - " + offeringId));


        var favourite = new FavouriteOffering();
        favourite.setUser(user);
        favourite.setOffering(offering);

        var saved = favouriteOfferingRepository.save(favourite);

        return new FavouriteOfferingResponse(saved.getId(), user.getId(), offeringId);
    }
}
