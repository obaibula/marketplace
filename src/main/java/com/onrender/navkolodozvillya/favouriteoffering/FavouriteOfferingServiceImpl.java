package com.onrender.navkolodozvillya.favouriteoffering;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteOfferingServiceImpl implements FavouriteOfferingService {
    private final FavouriteOfferingRepository favouriteOfferingRepository;
    @Override
    public List<FavouriteOfferingResponse> findAllBy(Principal principal) {
        String userEmail = principal.getName();
        return favouriteOfferingRepository.findAllByUserEmail(userEmail);
    }
}
