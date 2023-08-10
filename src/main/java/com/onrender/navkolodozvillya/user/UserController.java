package com.onrender.navkolodozvillya.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Controller", description = "User related APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin //todo: expose to appropriate domains
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get user by ID",
            description = "Retrieve a user by their ID")
    @GetMapping("/{userId}")
    public UserResponse getOne(@PathVariable Long userId){
        return userService.findById(userId);
    }
}
