package com.onrender.navkolodozvillya.offering;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/offerings")
@RequiredArgsConstructor
public class OfferingController {
    private final OfferingService offeringService;

    @GetMapping
    public ResponseEntity<List<OfferingResponse>> getAll(Pageable pageable){
        var offerings = offeringService.findAll(createPageRequest(pageable));

        return ResponseEntity.ok(offerings);
    }

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
