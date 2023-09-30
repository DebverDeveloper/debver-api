package com.donatoordep.debver.services.business.rules.anime.addInMyCart.validations;

import com.donatoordep.debver.services.business.rules.anime.addInMyCart.AddAnimeInMyCartArgs;
import com.donatoordep.debver.services.business.rules.anime.addInMyCart.AddAnimeValidation;
import com.donatoordep.debver.services.exceptions.NotFoundEntityException;
import org.springframework.stereotype.Component;

@Component
public class AnimeNotExistsValidation implements AddAnimeValidation {

    @Override
    public void verification(AddAnimeInMyCartArgs args) {
        if (args.repository().findById(args.dto().getAnimeId()).isEmpty()) {
            throw new NotFoundEntityException();
        }
    }
}
