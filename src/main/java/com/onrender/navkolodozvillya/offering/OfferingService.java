package com.onrender.navkolodozvillya.offering;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfferingService {
    List<OfferingResponse> findAll(Pageable pageable);

    OfferingResponse findById(Long offeringId);
}
