package com.onrender.navkolodozvillya.offering;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Offering Controller", description = "Offering related APIs")
@RestController
@RequestMapping("/offerings")
@RequiredArgsConstructor
@CrossOrigin //todo: expose to appropriate domains
public class OfferingController {
    private final OfferingService offeringService;

    @Operation(summary = "Get all offerings",
            description = "Retrieve a list of all offerings with paging and sorting")
    @GetMapping
    public ResponseEntity<List<OfferingResponse>> getAll(Pageable pageable){
        var offerings = offeringService.findAll(createPageRequest(pageable));

        return ResponseEntity.ok(offerings);
    }

    @Operation(summary = "Get offering by ID",
            description = "Retrieve an offering by its ID")
    @GetMapping("/{offeringId}")
    public ResponseEntity<OfferingResponse> getOne(@PathVariable Long offeringId){
        var offeringResponse = offeringService.findById(offeringId);
        return ResponseEntity.ok(offeringResponse);

    }

    private Pageable createPageRequest(Pageable pageable) {
        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id")));
    }
}
