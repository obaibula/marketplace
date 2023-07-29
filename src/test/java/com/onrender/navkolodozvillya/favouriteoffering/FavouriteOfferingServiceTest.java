package com.onrender.navkolodozvillya.favouriteoffering;

import com.onrender.navkolodozvillya.exception.entity.offering.OfferingIsAlreadyInFavoritesException;
import com.onrender.navkolodozvillya.exception.entity.offering.OfferingNotFoundException;
import com.onrender.navkolodozvillya.offering.OfferingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FavouriteOfferingServiceTest {
    @Mock
    private FavouriteOfferingRepository favouriteOfferingRepository;
    @Mock
    OfferingRepository offeringRepository;
    @Mock
    private Principal principal;
    private FavouriteOfferingService underTest;

    @BeforeEach
    void setUp(){
        underTest = new FavouriteOfferingService(favouriteOfferingRepository, offeringRepository);
    }

    @Test
    public void shouldFindAllByPrincipalEmail(){
        // given
        var email = "user@mail.com";
        given(principal.getName())
                .willReturn(email);
        var favourites =
                List.of(
                        new FavouriteOfferingResponse(1L, 1L, 1L),
                        new FavouriteOfferingResponse(2L, 2L, 1L));
        given(favouriteOfferingRepository.findAllByUserEmail(anyString()))
                .willReturn(favourites);
        // when
        var result = underTest.findAllBy(principal);
        // then
        verify(favouriteOfferingRepository, times(1)).findAllByUserEmail(email);
        assertThat(result).hasSize(2);
    }

    @Test
    public void shouldSaveToFavourites(){
        // given
        var email = "user@mail.com";
        given(principal.getName())
                .willReturn(email);
        given(favouriteOfferingRepository.existsByUserEmailAndOfferingId(anyString(), anyLong()))
                .willReturn(false);
        var expected = new FavouriteOfferingResponse(5L, 2L, 3L);
        given(offeringRepository.existsById(anyLong()))
                .willReturn(true);
        given(favouriteOfferingRepository.findByUserEmailAndOfferingId(anyString(), anyLong()))
                .willReturn(expected);

        // when
        var result = underTest.save(3L, principal);
        // then
        verify(favouriteOfferingRepository, times(1))
                .existsByUserEmailAndOfferingId(anyString(), anyLong());
        verify(favouriteOfferingRepository, times(1))
                .saveByUserEmailAndOfferingIdUsingNativeQuery(anyString(), anyLong());
        verify(favouriteOfferingRepository, times(1))
                .findByUserEmailAndOfferingId(email, 3L);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldThrowExceptionWhenItIsAlreadyInFavourites(){
        // given
        var email = "user@mail.com";
        given(principal.getName())
                .willReturn(email);
        given(offeringRepository.existsById(anyLong()))
                .willReturn(true);
        given(favouriteOfferingRepository.existsByUserEmailAndOfferingId(anyString(), anyLong()))
                .willReturn(true);
        // when
        assertThatThrownBy(() -> underTest.save(1L, principal))
                .isInstanceOf(OfferingIsAlreadyInFavoritesException.class)
                .hasMessage("Offering is already in favourites.");
    }

    @Test
    public void shouldThrowWhenOfferingDoesNotExist(){
        // given
        var email = "user@mail.com";
        var offeringId = 500L;
        given(principal.getName())
                .willReturn(email);
        given(offeringRepository.existsById(anyLong()))
                .willReturn(false);
        // then
        assertThatThrownBy(() -> underTest.save(offeringId, principal))
                .isInstanceOf(OfferingNotFoundException.class)
                .hasMessage("Offering not found with id - " + offeringId);
    }
}
