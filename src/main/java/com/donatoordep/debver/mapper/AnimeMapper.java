package com.donatoordep.debver.mapper;

import com.donatoordep.debver.builders.AnimeBuilder;
import com.donatoordep.debver.dto.request.AnimeRequestDTO;
import com.donatoordep.debver.dto.response.AnimeResponseDTO;
import com.donatoordep.debver.entities.Anime;
import com.donatoordep.debver.repositories.CategoriesRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnimeMapper {

    public Anime fromAnimeRequestDTOToEntity(AnimeRequestDTO animeDTO, CategoriesRepository repository) {
        return AnimeBuilder.builder()
                .title(animeDTO.getTitle())
                .imgUrl(animeDTO.getImgUrl())
                .authorName(animeDTO.getAuthorName())
                .description(animeDTO.getDescription())
                .episodes(animeDTO.getEpisodes())
                .categories(repository, animeDTO.getCategories())
                .status(animeDTO.getStatus())
                .build();
    }

    public AnimeResponseDTO fromEntityToResponseDTO(Anime anime) {
        return new AnimeResponseDTO(anime);
    }

    public List<AnimeResponseDTO> fromListEntityToListDTO(List<Anime> animes) {
        return animes.stream().map(this::fromEntityToResponseDTO).toList();
    }

    public Page<AnimeResponseDTO> fromPageEntityToPageDTO(Page<Anime> animes) {
        return animes.map(AnimeResponseDTO::new);
    }
}