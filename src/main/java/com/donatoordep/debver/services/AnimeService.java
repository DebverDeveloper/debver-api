package com.donatoordep.debver.services;

import com.donatoordep.debver.dto.request.AnimeRequestDTO;
import com.donatoordep.debver.dto.response.AnimeResponseDTO;
import com.donatoordep.debver.mapper.AnimeMapper;
import com.donatoordep.debver.repositories.AnimeRepository;
import com.donatoordep.debver.repositories.CategoriesRepository;
import com.donatoordep.debver.services.business.rules.anime.create.CreateAnimeArgs;
import com.donatoordep.debver.services.business.rules.anime.create.CreateAnimeValidation;
import com.donatoordep.debver.services.exceptions.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnimeService {

    @Autowired
    private AnimeMapper animeMapper;

    @Autowired
    private AnimeRepository repository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private List<CreateAnimeValidation> animeValidations;

    @Transactional
    public AnimeResponseDTO createAnime(AnimeRequestDTO dto) {
        animeValidations.forEach(v -> v.validation(new CreateAnimeArgs(dto, repository)));
        return animeMapper.fromEntityToResponseDTO(
                repository.save(animeMapper.fromAnimeRequestDTOToEntity(dto, categoriesRepository))
        );
    }

    @Transactional(readOnly = true)
    public List<AnimeResponseDTO> findByName(String name) {
        return animeMapper.fromListEntityToListDTO(repository.findByName(name));
    }

    @Transactional(readOnly = true)
    public Page<AnimeResponseDTO> findAll(Pageable pageable) {
        return animeMapper.fromPageEntityToPageDTO(repository.findAll(pageable));
    }

    public AnimeResponseDTO findById(Long id) {
        return new AnimeResponseDTO(repository.findById(id).orElseThrow(NotFoundEntityException::new));
    }
}