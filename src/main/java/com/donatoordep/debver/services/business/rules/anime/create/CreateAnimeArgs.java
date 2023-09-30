package com.donatoordep.debver.services.business.rules.anime.create;

import com.donatoordep.debver.dto.request.AnimeRequestDTO;
import com.donatoordep.debver.repositories.AnimeRepository;

public record CreateAnimeArgs(AnimeRequestDTO dto, AnimeRepository repository) {
}
