package com.onrender.navkolodozvillya.offering;

import com.onrender.navkolodozvillya.exception.OfferingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OfferingServiceImpl implements OfferingService {
    private final OfferingRepository offeringRepository;
    private final OfferingResponseMapper mapToResponseDto;

    @Override
    public List<OfferingResponse> findAll(Pageable pageable) {
        return offeringRepository.findAll(pageable)
                .map(mapToResponseDto)
                .toList();
    }

    @Override
    public OfferingResponse findById(Long offeringId) {
        return offeringRepository.findById(offeringId)
                .map(mapToResponseDto)
                .orElseThrow(() -> new OfferingNotFoundException("Offering not found with id - " + offeringId));
    }
}
