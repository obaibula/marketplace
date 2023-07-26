package com.onrender.navkolodozvillya.favouriteoffering;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/favourites")
@RequiredArgsConstructor
public class FavouriteOfferingController {
    private final FavouriteOfferingService favouriteOfferingService;

    @GetMapping
    public ResponseEntity<List<FavouriteOfferingResponse>> findAll(Principal principal) {
        return ResponseEntity.ok(favouriteOfferingService.findAllBy(principal));
    }

    @PostMapping("/{offeringId}")
    public ResponseEntity<FavouriteOfferingResponse>
    addOfferingToFavourites(@PathVariable Long offeringId,
                            Principal principal){
        var savedFavourite = favouriteOfferingService.save(offeringId, principal);

        return created(getLocation(savedFavourite))
                .body(savedFavourite);
    }

    private URI getLocation(FavouriteOfferingResponse response) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
    }
}
