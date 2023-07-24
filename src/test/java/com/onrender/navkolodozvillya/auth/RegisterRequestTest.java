package com.onrender.navkolodozvillya.auth;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RegisterRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void firstNameShouldNotContainNonUkrainianCharacters(){
        // given
        var request = new RegisterRequest(
                "name",
                null,
                "email@mail.com",
                "Aa12345#1"
        );
        // when
        var violations = validator.validate(request);
        // then
        assertThat(violations.size()).isEqualTo(1);
        var errorMessage = violations.iterator().next().getMessage();
        assertThat(errorMessage)
                .isEqualTo("Invalid first name: must contain only Ukrainian characters");
    }

    @Test
    public void lastNameShouldNotContainNonUkrainianCharacters(){
        // given
        var request = new RegisterRequest(
                null,
                "name",
                "email@mail.com",
                "Aa12345#1"
        );
        // when
        var violations = validator.validate(request);
        // then
        assertThat(violations.size()).isEqualTo(1);
        var errorMessage = violations.iterator().next().getMessage();
        assertThat(errorMessage)
                .isEqualTo("Invalid last name: must contain only Ukrainian characters");
    }

    @Test
    public void emailShouldBeValid(){
        // given
        var request = new RegisterRequest(
                null,
                null,
                "email.com",
                "Aa12345#1"
        );
        // when
        var violations = validator.validate(request);
        // then
        assertThat(violations.size()).isEqualTo(1);
        var errorMessage = violations.iterator().next().getMessage();
        assertThat(errorMessage)
                .isEqualTo("Invalid email");
    }

    @Test
    public void emailShouldNotBeNull(){
        // given
        var request = new RegisterRequest(
                null,
                null,
                null,
                "Aa12345#1"
        );
        // when
        var violations = validator.validate(request);
        // then
        assertThat(violations.size()).isEqualTo(1);
        var errorMessage = violations.iterator().next().getMessage();
        assertThat(errorMessage)
                .isEqualTo("Email must not be null");
    }
    @Test
    public void passwordShouldNotBeNull(){
        // given
        var request = new RegisterRequest(
                null,
                null,
                "email@mail.com",
                null
        );
        // when
        var violations = validator.validate(request);
        // then
        assertThat(violations.size()).isEqualTo(1);
        var errorMessage = violations.iterator().next().getMessage();
        assertThat(errorMessage)
                .isEqualTo("Password must not be null");
    }

    @Test
    public void passwordShouldBeValid(){
        // given
        var request = new RegisterRequest(
                null,
                null,
                "email@mail.com",
                "12345678"
        );
        // when
        var violations = validator.validate(request);
        // then
        assertThat(violations.size()).isEqualTo(1);
        var errorMessage = violations.iterator().next().getMessage();
        assertThat(errorMessage)
                .isEqualTo("Invalid password: must contain numbers, " +
                        "lowercase and uppercase latin letters, " +
                        "and special symbols");

    }

    @Test
    public void passwordShouldBeLongerThan8AndShorterThan64Characters(){
        // given
        var request = new RegisterRequest(
                null,
                null,
                "email@mail.com",
                "aA#1234"
        );
        // when
        var violations = validator.validate(request);
        // then
        assertThat(violations.size()).isEqualTo(1);
        var errorMessage = violations.iterator().next().getMessage();
        assertThat(errorMessage)
                .isEqualTo("Invalid password: must be of 8-64 characters");
    }


}