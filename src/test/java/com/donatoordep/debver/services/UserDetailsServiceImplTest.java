package com.donatoordep.debver.services;

import com.donatoordep.debver.builders.UserBuilder;
import com.donatoordep.debver.entities.User;
import com.donatoordep.debver.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserDetailsServiceImpl service;

    @Test
    @DisplayName("Given UserDetails Object When FindByEmailForUserDetails Is Called Should Return UserDetails")
    void testGiven_UserDetails_Object_When_FindByEmailForUserDetails_Is_Called_Should_Return_UserDetails() {

        User user = UserBuilder.builder()
                .name("Pedro")
                .email("pedro@gmail.com")
                .password("123456")
                .profile("http://img.com", "Sou o Pedro")
                .build();

        when(repository.findByEmailForUserDetails(user.getEmail())).thenReturn((UserDetails) user);
        UserDetails output = service.loadUserByUsername(user.getEmail());

        Assertions.assertEquals(user.getEmail(), output.getUsername(),
                () -> String.format("Expected: %s\nActual: %s", user.getEmail(), output.getUsername()));
    }
}
