package com.donatoordep.debver.builders.dto.response;


import com.donatoordep.debver.dto.response.AnimeOrderDetailsResponseDTO;
import com.donatoordep.debver.dto.response.AnimeResponseDTO;
import com.donatoordep.debver.enums.StatusOrder;

public class AnimeOrderDetailsResponseDTOBuilder {

    private AnimeOrderDetailsResponseDTO animeResponseDTO;

    private AnimeOrderDetailsResponseDTOBuilder() {
        this.animeResponseDTO = new AnimeOrderDetailsResponseDTO();
    }

    public static AnimeOrderDetailsResponseDTOBuilder builder(){
        return new AnimeOrderDetailsResponseDTOBuilder();
    }

    public AnimeOrderDetailsResponseDTO build(){
        return animeResponseDTO;
    }

    public AnimeOrderDetailsResponseDTOBuilder id(AnimeResponseDTO anime){
        this.animeResponseDTO.setAnime(anime);
        return this;
    }
    public AnimeOrderDetailsResponseDTOBuilder status(StatusOrder status){
        this.animeResponseDTO.setStatusOrder(status);
        return this;
    }

    public AnimeOrderDetailsResponseDTOBuilder episode(int episode) {
        this.animeResponseDTO.setEpisode(episode);
        return this;
    }
}
