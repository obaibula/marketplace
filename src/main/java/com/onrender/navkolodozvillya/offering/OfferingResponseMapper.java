package com.onrender.navkolodozvillya.offering;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OfferingResponseMapper
        implements Function<Offering, OfferingResponse> {
    @Override
    public OfferingResponse apply(Offering offering) {
        return new OfferingResponse(
                offering.getId(),
                offering.getName(),
                offering.getDescription(),
                offering.getPrice(),
                offering.getCategory()
        );
    }
}
