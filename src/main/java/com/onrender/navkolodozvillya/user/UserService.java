package com.onrender.navkolodozvillya.user;

import com.onrender.navkolodozvillya.exception.entity.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;
    public UserResponse findById(Long userId) {
        return userRepository.findById(userId)
                .map(userResponseMapper::UserToUserResponseDto)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id - " + userId));
    }
}
