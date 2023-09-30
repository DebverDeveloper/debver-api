package com.donatoordep.debver.controllers;

import com.donatoordep.debver.dto.request.AnimeOrderDetailsRequestDTO;
import com.donatoordep.debver.dto.response.AnimeOrderDetailsResponseDTO;
import com.donatoordep.debver.entities.User;
import com.donatoordep.debver.services.AnimeOrderDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/orders")
public class OrderController {

    @Autowired
    private AnimeOrderDetailsService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnimeOrderDetailsResponseDTO> addAnimeInMyCart(
            @Valid @RequestBody AnimeOrderDetailsRequestDTO dto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(service.addAnimeInMyCart(dto, user));
    }
}
