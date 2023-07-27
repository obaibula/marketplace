package com.onrender.navkolodozvillya.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {
    UserResponse UserToUserResponseDto(User user);
}
