package com.donatoordep.debver.services.business.rules.anime.addInMyCart.validations;

import com.donatoordep.debver.dto.response.AnimeResponseDTO;
import com.donatoordep.debver.services.AnimeService;
import com.donatoordep.debver.services.business.rules.anime.addInMyCart.AddAnimeInMyCartArgs;
import com.donatoordep.debver.services.business.rules.anime.addInMyCart.AddAnimeValidation;
import com.donatoordep.debver.services.exceptions.IncompatibleEpisodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EpisodeNotIsValidValidation implements AddAnimeValidation {

    @Autowired
    AnimeService service;

    @Override
    public void verification(AddAnimeInMyCartArgs args) {
        AnimeResponseDTO anime = service.findById((args.dto().getAnimeId()));

        if (args.dto().getEpisode() > anime.getEpisodes()) {
            throw new IncompatibleEpisodeException();
        }
    }
}
