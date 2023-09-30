package com.donatoordep.debver.services;

import com.donatoordep.debver.dto.request.AnimeOrderDetailsRequestDTO;
import com.donatoordep.debver.dto.response.AnimeOrderDetailsResponseDTO;
import com.donatoordep.debver.entities.Anime;
import com.donatoordep.debver.entities.AnimeOrder;
import com.donatoordep.debver.entities.AnimeOrderDetails;
import com.donatoordep.debver.entities.User;
import com.donatoordep.debver.enums.StatusOrder;
import com.donatoordep.debver.repositories.AccountStatsRepository;
import com.donatoordep.debver.repositories.AnimeOrderDetailsRepository;
import com.donatoordep.debver.repositories.AnimeOrderRepository;
import com.donatoordep.debver.repositories.AnimeRepository;
import com.donatoordep.debver.services.business.rules.anime.addInMyCart.AddAnimeInMyCartArgs;
import com.donatoordep.debver.services.business.rules.anime.addInMyCart.AddAnimeValidation;
import com.donatoordep.debver.utils.ConvertingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeOrderDetailsService {

    @Autowired
    private AnimeOrderDetailsRepository detailsRepository;

    @Autowired
    private AnimeOrderRepository animeOrderRepository;

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private List<AddAnimeValidation> validations;

    @Autowired
    private AccountStatsRepository accountStatsRepository;

    public AnimeOrderDetailsResponseDTO addAnimeInMyCart(AnimeOrderDetailsRequestDTO dto, User user) {
        validations.forEach(v -> v.verification(new AddAnimeInMyCartArgs(dto, animeRepository, user)));

        Anime anime = animeRepository.findById(dto.getAnimeId()).get();

        AnimeOrderDetails animeOrderDetails = new AnimeOrderDetails(
                anime, dto.getEpisode(), ConvertingType.convertStringToEnum(
                StatusOrder.class, dto.getStatus()));

        animeOrderDetails = detailsRepository.save(animeOrderDetails);
        AnimeOrder animeOrder = new AnimeOrder(animeOrderDetails, user.getCart());
        animeOrderDetails.setAnimeOrder(animeOrder);
        user.addAnime(animeOrder);

        userService.update(user);

        return new AnimeOrderDetailsResponseDTO(animeOrderDetails);
    }
}
