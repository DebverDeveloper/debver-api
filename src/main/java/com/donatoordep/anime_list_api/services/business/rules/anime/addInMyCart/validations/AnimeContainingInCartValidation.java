package com.donatoordep.anime_list_api.services.business.rules.anime.addInMyCart.validations;

import com.donatoordep.anime_list_api.services.business.rules.anime.addInMyCart.AddAnimeInMyCartArgs;
import com.donatoordep.anime_list_api.services.business.rules.anime.addInMyCart.AddAnimeValidation;
import com.donatoordep.anime_list_api.services.exceptions.AnimeAlreadyInCartException;
import org.springframework.stereotype.Component;

@Component
public class AnimeContainingInCartValidation implements AddAnimeValidation {

    @Override
    public void verification(AddAnimeInMyCartArgs args) {


        args.user().getCart().getFavorites().forEach(
                obj1 -> obj1.getAnimeOrderDetails()
                        .forEach(obj2 -> {
                            if (obj2.getAnime().getId().equals(args.dto().getAnimeId())) {
                                throw new AnimeAlreadyInCartException(obj2.getAnime().getId());
                            }
                        }));
    }
}
