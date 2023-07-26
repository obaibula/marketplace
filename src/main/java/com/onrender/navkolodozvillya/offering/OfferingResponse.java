package com.onrender.navkolodozvillya.offering;

import java.math.BigDecimal;

public record OfferingResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        OfferingCategory category) {}