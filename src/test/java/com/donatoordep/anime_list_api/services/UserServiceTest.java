package com.donatoordep.anime_list_api.services;

import com.donatoordep.anime_list_api.dto.request.ProfileUserRequestDTO;
import com.donatoordep.anime_list_api.dto.request.UserRequestDTO;
import com.donatoordep.anime_list_api.dto.response.AccountStatsResponseDTO;
import com.donatoordep.anime_list_api.dto.response.CartResponseDTO;
import com.donatoordep.anime_list_api.dto.response.ProfileUserResponseDTO;
import com.donatoordep.anime_list_api.dto.response.UserResponseDTO;
import com.donatoordep.anime_list_api.entities.User;
import com.donatoordep.anime_list_api.mappers.UserMapper;
import com.donatoordep.anime_list_api.repositories.UserRepository;
import com.donatoordep.anime_list_api.services.business.rules.user.register.RegisterUserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    List<RegisterUserValidation> userRegisterValidations;

    @Mock
    PasswordEncoder encoder;

    @Mock
    RoleService roleService;

    @Mock
    UserMapper mapper;

    @InjectMocks
    UserService service;

    User user;

    @BeforeEach
    void setup() {
        user = new User(
                "Pedro",
                "pedro@gmail.com",
                "123",
                "http://img.com",
                "Sou o Pedro");
        user.setId(1L);
    }

    @Test
    @DisplayName("Display name")
    void testGivenUserResponseDTO_When_RegisterIsCalled_ShouldReturn_UserResponseDTO() {

        UserRequestDTO dto = new UserRequestDTO("Pedro", "pedro@gmail.com", "123");
        dto.setProfile(new ProfileUserRequestDTO("imagembonit", "minha bio"));

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
        ProfileUserResponseDTO profileUserDTO = new ProfileUserResponseDTO(user.getProfile());
        profileUserDTO.setAccountStats(new AccountStatsResponseDTO(user.getProfile().getAnimeStats()));

        userResponseDTO.setProfile(profileUserDTO);
        userResponseDTO.setCart(new CartResponseDTO(user.getCart()));

        User user = new User(dto.getName(), dto.getEmail(), encoder.encode(dto.getPassword()),
                dto.getProfile().getImgUrl(), dto.getProfile().getBio());

        when(mapper.toDto(repository.save(user))).thenReturn(userResponseDTO);

        UserResponseDTO output = service.register(dto);

        assertNotNull(output, () -> "The return can´t not");
        assertEquals(userResponseDTO, output, () -> "The object not equals, should returned same object");
        assertTrue(output.getId() > 0, () -> "The id not is valid");
    }
}
