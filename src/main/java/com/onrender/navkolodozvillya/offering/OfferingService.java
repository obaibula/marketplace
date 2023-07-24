package com.onrender.navkolodozvillya.offering;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfferingService {
    List<Offering> findAll(Pageable pageable);
}
