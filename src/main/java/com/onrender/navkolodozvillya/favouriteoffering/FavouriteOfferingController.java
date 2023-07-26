package com.onrender.navkolodozvillya.favouriteoffering;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/favourites")
@RequiredArgsConstructor
public class FavouriteOfferingController {
    private final FavouriteOfferingService favouriteOfferingService;

    @GetMapping
    public ResponseEntity<List<FavouriteOfferingResponse>> findAll(Principal principal){
        return ResponseEntity.ok(favouriteOfferingService.findAllBy(principal));
    }
}
