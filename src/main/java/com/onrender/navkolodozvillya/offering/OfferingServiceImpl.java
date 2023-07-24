package com.onrender.navkolodozvillya.offering;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferingServiceImpl implements OfferingService {
    private final OfferingRepository offeringRepository;
    @Override
    public List<Offering> findAll(Pageable pageable) {
        return offeringRepository.findAll(pageable)
                .toList();
    }
}
