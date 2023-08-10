package com.onrender.navkolodozvillya.favouriteoffering;

import com.onrender.navkolodozvillya.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;

@Tag(name = "Favourite Offering Controller",
        description = "Favourite offering related APIs")
@RestController
@RequestMapping("/favourites")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin //todo: expose to appropriate domains
public class FavouriteOfferingController {
    private final FavouriteOfferingService favouriteOfferingService;

    @Operation(summary = "Get all favourite offerings",
            description = "Retrieve a list of favourite offerings for the authenticated user")
    @GetMapping
            public ResponseEntity<List<FavouriteOfferingResponse>> findAll(
                    @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(favouriteOfferingService.findAllBy(user));
    }

    @Operation(summary = "Add offering to favourites",
            description = "Add a new offering to the user's favourites")
    @PostMapping("/{offeringId}")
    public ResponseEntity<FavouriteOfferingResponse>
    addOfferingToFavourites(@PathVariable Long offeringId,
                            @AuthenticationPrincipal User user) {
        var savedFavourite = favouriteOfferingService.save(offeringId, user);

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
