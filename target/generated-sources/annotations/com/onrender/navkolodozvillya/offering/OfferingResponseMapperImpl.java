package com.onrender.navkolodozvillya.offering;

import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-08T06:57:21+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Private Build)"
)
@Component
public class OfferingResponseMapperImpl implements OfferingResponseMapper {

    @Override
    public OfferingResponse offeringToOfferingResponseDto(Offering offering) {
        if ( offering == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        BigDecimal price = null;
        OfferingCategory category = null;

        id = offering.getId();
        name = offering.getName();
        description = offering.getDescription();
        price = offering.getPrice();
        category = offering.getCategory();

        OfferingResponse offeringResponse = new OfferingResponse( id, name, description, price, category );

        return offeringResponse;
    }
}
