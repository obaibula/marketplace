package com.onrender.navkolodozvillya.favouriteoffering;

import com.onrender.navkolodozvillya.exception.entity.offering.OfferingIsAlreadyInFavoritesException;
import com.onrender.navkolodozvillya.exception.entity.offering.OfferingNotFoundException;
import com.onrender.navkolodozvillya.offering.Offering;
import com.onrender.navkolodozvillya.offering.OfferingRepository;
import com.onrender.navkolodozvillya.user.User;
import com.onrender.navkolodozvillya.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FavouriteOfferingServiceTest {
    @Mock
    OfferingRepository offeringRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    private FavouriteOfferingRepository favouriteOfferingRepository;
    @Mock
    private Principal principal;
    private FavouriteOfferingService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FavouriteOfferingService(favouriteOfferingRepository, offeringRepository, userRepository);
    }

    @Test
    public void shouldFindAllByPrincipalEmail() {
        // given
        var user = new User();
        user.setId(2L);
        var favourites =
                List.of(
                        new FavouriteOfferingResponse(1L, 2L, 3L),
                        new FavouriteOfferingResponse(2L, 2L, 1L));
        given(favouriteOfferingRepository.findAllByUserId(anyLong()))
                .willReturn(favourites);
        // when
        var result = underTest.findAllBy(user);
        // then
        verify(favouriteOfferingRepository, times(1)).findAllByUserId(anyLong());
        assertThat(result).hasSize(2);
    }

    @Test
    public void shouldSaveToFavourites() {
        // given
        var email = "user@mail.com";
        var user = new User();
        user.setId(2L);
        var offering = new Offering();
        offering.setId(3L);
        given(offeringRepository.existsById(anyLong()))
                .willReturn(true);

        given(favouriteOfferingRepository.existsByUserIdAndOfferingId(anyLong(), anyLong()))
                .willReturn(false);
        var expected = new FavouriteOffering();
        expected.setUser(user);
        expected.setOffering(offering);

        given(favouriteOfferingRepository.save(any(FavouriteOffering.class)))
                .willReturn(expected);


        // when
        var result = underTest.save(3L, user);
        // then
        verify(favouriteOfferingRepository, times(1))
                .existsByUserIdAndOfferingId(anyLong(), anyLong());
        verify(userRepository, times(1))
                .getReferenceById(anyLong());
        verify(offeringRepository, times(1))
                .getReferenceById(anyLong());


        assertThat(result.userId()).isEqualTo(expected.getUser().getId());
        assertThat(result.offeringId()).isEqualTo(expected.getOffering().getId());
    }

    @Test
    public void shouldThrowExceptionWhenItIsAlreadyInFavourites() {
        // given
        var email = "user@mail.com";
        var user = new User();
        user.setId(2L);
        var offering = new Offering();
        offering.setId(3L);
        given(offeringRepository.existsById(anyLong()))
                .willReturn(true);

        given(favouriteOfferingRepository.existsByUserIdAndOfferingId(anyLong(), anyLong()))
                .willReturn(true);
        // when
        assertThatThrownBy(() -> underTest.save(3L, user))
                .isInstanceOf(OfferingIsAlreadyInFavoritesException.class)
                .hasMessage("Offering is already in favourites.");
    }

    @Test
    public void shouldThrowExceptionWhenOfferingDoesNotExist() {
        // given
        given(offeringRepository.existsById(anyLong()))
                .willReturn(false);
        // when
        assertThatThrownBy(() -> underTest.save(3L, new User()))
                .isInstanceOf(OfferingNotFoundException.class)
                .hasMessage("Offering not found with id: 3");
    }
}
