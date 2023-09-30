package com.donatoordep.debver.controllers;

import com.donatoordep.debver.dto.request.AuthenticationRequestDTO;
import com.donatoordep.debver.dto.request.UserRequestDTO;
import com.donatoordep.debver.dto.response.AuthenticationResponseDTO;
import com.donatoordep.debver.dto.response.UserResponseDTO;
import com.donatoordep.debver.entities.Code;
import com.donatoordep.debver.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/v1/auth")
public class AuthController {

    @Autowired
    private UserService service;

    @PostMapping(path = "/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO dto) throws MessagingException {
        UserResponseDTO objectCreated = service.register(dto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(objectCreated.getId()).toUri()).body(objectCreated);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody AuthenticationRequestDTO dto) {
        return ResponseEntity.ok().body(service.login(dto));
    }

    @PostMapping(path = "/verify")
    public ResponseEntity<Code> verifyAccount(@Valid @RequestBody Code code) {
        return ResponseEntity.ok().body(service.verifyAccount(code));
    }
}