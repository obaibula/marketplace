package com.onrender.navkolodozvillya.offering;

import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface OfferingResponseMapper {
    OfferingResponse offeringToOfferingResponseDto(Offering offering);
}
