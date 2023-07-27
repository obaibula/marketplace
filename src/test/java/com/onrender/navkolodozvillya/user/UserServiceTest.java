package com.onrender.navkolodozvillya.user;

import com.onrender.navkolodozvillya.exception.entity.user.UserNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserResponseMapper userResponseMapper;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository, userResponseMapper);
    }

    @Test
    public void shouldFindUserById(){
        // given
        var user = getUser();

        given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(user));
        given(userResponseMapper.UserToUserResponseDto(any(User.class)))
                .willAnswer(getUserResponseAnswer());
        // when
        Long userId = user.getId();
        var result = underTest.findById(userId);
        // then
        verify(userRepository, times(1)).findById(userId);
        verify(userResponseMapper, times(1))
                .UserToUserResponseDto(any(User.class));
        assertThat(result.email()).isEqualTo(user.getEmail());
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotFound(){
        //given
        var userId = 500L;
        //then
        assertThatThrownBy(() -> underTest.findById(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id - " + userId);
    }

    @NotNull
    private Answer<Object> getUserResponseAnswer() {
        return invocation -> {
            User user = invocation.getArgument(0);
            return new UserResponse(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRole()
            );
        };
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .firstName("Oleh")
                .lastName("Baibula")
                .email("baibula@mail.com")
                .role(Role.CUSTOMER)
                .build();
    }
}