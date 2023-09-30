package com.donatoordep.debver.services.business.rules.anime.addInMyCart;

import com.donatoordep.debver.dto.request.AnimeOrderDetailsRequestDTO;
import com.donatoordep.debver.entities.User;
import com.donatoordep.debver.repositories.AnimeRepository;

public record AddAnimeInMyCartArgs(AnimeOrderDetailsRequestDTO dto, AnimeRepository repository, User user) {
}
