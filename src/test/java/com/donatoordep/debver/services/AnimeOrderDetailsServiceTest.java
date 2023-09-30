package com.donatoordep.debver.services;

import com.donatoordep.debver.builders.AnimeBuilder;
import com.donatoordep.debver.builders.UserBuilder;
import com.donatoordep.debver.dto.request.AnimeOrderDetailsRequestDTO;
import com.donatoordep.debver.dto.response.AnimeOrderDetailsResponseDTO;
import com.donatoordep.debver.entities.Anime;
import com.donatoordep.debver.entities.AnimeOrderDetails;
import com.donatoordep.debver.entities.User;
import com.donatoordep.debver.enums.Status;
import com.donatoordep.debver.enums.StatusOrder;
import com.donatoordep.debver.repositories.AccountStatsRepository;
import com.donatoordep.debver.repositories.AnimeOrderDetailsRepository;
import com.donatoordep.debver.repositories.AnimeOrderRepository;
import com.donatoordep.debver.repositories.AnimeRepository;
import com.donatoordep.debver.services.business.rules.anime.addInMyCart.AddAnimeValidation;
import com.donatoordep.debver.utils.ConvertingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnimeOrderDetailsServiceTest {

    @Mock
    AnimeOrderDetailsRepository detailsRepository;

    @Mock
    AnimeOrderRepository animeOrderRepository;

    @Mock
    AnimeRepository animeRepository;

    @Mock
    UserService userService;

    @Mock
    List<AddAnimeValidation> validations;

    @Mock
    AccountStatsRepository accountStatsRepository;

    @InjectMocks
    AnimeOrderDetailsService service;

    @Test
    @DisplayName("Given AnimeOrderDetailsResponseDTO When AddAnimeInMyCart Is Called")
    void tesGivenAnimeOrderDetailsResponseDTO_When_AddAnimeInMyCartIsCalled() {

        AnimeOrderDetailsRequestDTO animeOrderDetailsRequestDTO = new AnimeOrderDetailsRequestDTO(
                1L, StatusOrder.COMPLETED.toString(), 100);

        User user = UserBuilder.builder()
                .id(1L)
                .name("Pedro")
                .cart()
                .email("pedro@gmail.com")
                .password("123456")
                .profile("http://img.com", "Sou o Pedro")
                .build();

        Anime anime = AnimeBuilder.builder()
                .title("Attack on titan")
                .description("descrição gigante")
                .imgUrl("https://imagem.com")
                .authorName("Pedro Donato")
                .status(Status.AIRING)
                .episodes(150)
                .build();

        AnimeOrderDetails ot = new AnimeOrderDetails(anime, animeOrderDetailsRequestDTO.getEpisode(),
                ConvertingType.convertStringToEnum(
                        StatusOrder.class, animeOrderDetailsRequestDTO.getStatus()));
        ot.setId(1L);

        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(detailsRepository.save(any(AnimeOrderDetails.class))).thenReturn(ot);

        AnimeOrderDetailsResponseDTO output = service.addAnimeInMyCart(animeOrderDetailsRequestDTO, user);
        Assertions.assertNotNull(output, () -> "Can´t should return null object");
        Assertions.assertFalse(user.getCart().getFavorites().isEmpty(),
                () -> "Cart of user should have minimum one element");
    }
}
