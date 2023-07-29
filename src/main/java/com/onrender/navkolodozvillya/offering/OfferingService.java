package com.onrender.navkolodozvillya.offering;

import com.onrender.navkolodozvillya.exception.entity.offering.OfferingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferingService {
    private final OfferingRepository offeringRepository;
    private final OfferingResponseMapper offeringResponseMapper;

    public List<OfferingResponse> findAll(Pageable pageable) {
        return offeringRepository.findAll(pageable)
                .map(offeringResponseMapper::offeringToOfferingResponseDto)
                .toList();
    }

    public OfferingResponse findById(Long offeringId) {
        return offeringRepository.findById(offeringId)
                .map(offeringResponseMapper::offeringToOfferingResponseDto)
                .orElseThrow(() -> new OfferingNotFoundException("Offering not found with id - " + offeringId));
    }
}
