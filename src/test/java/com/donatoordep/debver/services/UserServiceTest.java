package com.donatoordep.debver.services;

import com.donatoordep.debver.builders.UserBuilder;
import com.donatoordep.debver.dto.request.ProfileUserRequestDTO;
import com.donatoordep.debver.dto.request.UserRequestDTO;
import com.donatoordep.debver.dto.response.AccountStatsResponseDTO;
import com.donatoordep.debver.dto.response.CartResponseDTO;
import com.donatoordep.debver.dto.response.ProfileUserResponseDTO;
import com.donatoordep.debver.dto.response.UserResponseDTO;
import com.donatoordep.debver.entities.User;
import com.donatoordep.debver.mapper.UserMapper;
import com.donatoordep.debver.repositories.UserRepository;
import com.donatoordep.debver.services.business.rules.user.findByName.FindByNameValidation;
import com.donatoordep.debver.services.business.rules.user.register.RegisterUserValidation;
import com.donatoordep.debver.services.exceptions.NotFoundEntityException;
import com.donatoordep.debver.services.exceptions.UserExistsInDatabaseException;
import com.donatoordep.debver.services.exceptions.WeakPasswordException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder encoder;

    @Mock
    RoleService roleService;

    @Mock
    List<RegisterUserValidation> userRegisterValidations;

    @Mock
    List<FindByNameValidation> findByNameValidations;

    @Mock
    UserMapper userMapper;

    @Mock
    MailService mailService;

    @InjectMocks
    UserService service;

    User user;
    UserResponseDTO userResponseDTO;
    UserRequestDTO userRequestDTO;

    @BeforeEach
    void setup() {
        user = UserBuilder.builder()
                .id(1L)
                .cart()
                .name("Pedro")
                .email("pedro@gmail.com")
                .password("123456")
                .profile("http://img.com", "sou o pedro")
                .build();

        userResponseDTO = new UserResponseDTO(user);
        ProfileUserResponseDTO profileUserDTO = new ProfileUserResponseDTO(user.getProfile());
        profileUserDTO.setAccountStats(new AccountStatsResponseDTO(user.getProfile().getAnimeStats()));

        userResponseDTO.setProfile(profileUserDTO);
        userResponseDTO.setCart(new CartResponseDTO(user.getCart()));

        userRequestDTO = new UserRequestDTO(user.getName(), user.getEmail(), user.getPassword());
        userRequestDTO.setProfile(new ProfileUserRequestDTO(user.getProfile().getImgUrl(), user.getProfile().getBio()));
    }

    @Test
    @DisplayName("GivenUserRequestDTO When Register Is Called Should Return UserResponseDTO")
    void testGivenUserRequestDTO_When_RegisterIsCalled_ShouldReturn_UserResponseDTO() throws MessagingException {

        when(userMapper.convertUserRequestDTOToUser(userRequestDTO)).thenReturn(user);
        when(userMapper.convertUserToUserResponseDTO(repository.save(user))).thenReturn(userResponseDTO);

        UserResponseDTO output = service.register(userRequestDTO);

        assertNotNull(output, () -> "The return can´t not");
        assertEquals(userResponseDTO, output, () -> "The object not equals, should returned same object");
        assertTrue(output.getId() > 0, () -> "The id not is valid");
    }

    @Test
    @DisplayName("Given User Object When Email Exists Throw UserExistsInDatabaseException")
    void testGivenUserObject_When_EmailExists_ThrowUserExistsInDatabaseException() throws MessagingException {

        when(userMapper.convertUserRequestDTOToUser(userRequestDTO)).thenReturn(user);
        when(service.register(userRequestDTO)).thenThrow(UserExistsInDatabaseException.class);

        assertThrows(UserExistsInDatabaseException.class, () -> service.register(userRequestDTO),
                () -> "Email has exists, should return throw exception");
    }

    @Test
    @DisplayName("Given User Object When Password Size < 8 Throw WeakPasswordException")
    void testGivenUserObject_When_PasswordSizeLessFive_Throw_WeakPasswordException() throws MessagingException {

        userRequestDTO.setPassword("123");
        user.setPassword("123");

        when(userMapper.convertUserRequestDTOToUser(userRequestDTO)).thenReturn(user);
        when(service.register(userRequestDTO)).thenThrow(WeakPasswordException.class);

        assertThrows(WeakPasswordException.class, () -> service.register(userRequestDTO),
                () -> "Password minimum 8 characters, should return throw exception");
    }

    @Test
    @DisplayName("Given User Object When MyCart Is Called Should Return CartResponseDTO")
    void testGivenUserObject_When_MyCartIsCalled_ShouldReturn_CartResponseDTO() {

        CartResponseDTO cartResponseDTO = new CartResponseDTO(user.getCart());
        CartResponseDTO output = service.myCart(user);

        assertEquals(output, cartResponseDTO, () -> "CartResponseDTO should return same cart");
    }

    @Test
    @DisplayName("Given Page<UserResponseDTO> When FindByName Is Called Should Return Page<UserResponseDTO>")
    void testGivenPageUserResponseDTO_When_FindByNameIsCalled_ShouldReturn_PageUserResponseDTO() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        Page<UserResponseDTO> userPage = new PageImpl<>(
                Collections.singletonList(userResponseDTO), pageable, 1);

        when(userMapper.convertUserPageToUserResponseDTOPage(
                repository.findByName(userResponseDTO.getName(), pageable))).thenReturn(userPage);

        Page<UserResponseDTO> output = service.findByName(user.getName(), pageable);

        assertNotNull(output);
        assertEquals(output.getContent().get(0).getId(), user.getId(),
                () -> "Id should return same id of user object");
        assertEquals(output.getContent().get(0).getName(), user.getName(),
                () -> "Name should return same name of user object");
    }

    @Test
    @DisplayName("Given Empty List When FindByName Is Called Should Throw NotFoundEntityException")
    void testGivenEmptyList_When_FindByNameIsCalled_ShouldThrow_NotFoundEntityException() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<User> userPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(repository.findByName(anyString(), eq(pageable))).thenThrow(NotFoundEntityException.class);

        assertThrows(NotFoundEntityException.class, () -> service.findByName("not-exists", pageable),
                () -> "FindByName should throw a exception NotFoundEntityException");
    }

    @Test
    @DisplayName("Given User Object When Me Is Called Should Return UserResponseDTO Object")
    void testGiven_UserObject_When_MeIsCalled_ShouldReturn_UserResponseDTOObject() {

        when(service.me(user)).thenReturn(userResponseDTO);

        UserResponseDTO output = service.me(user);

        assertNotNull(output, () -> "Can´t should returned nullable");
        assertEquals(userResponseDTO, output, () -> "Should return same data");
        assertTrue(output.getId() > 0, () -> "Id can´t be null");
    }

    @Test
    @DisplayName("Given User Object When Update Is Called Should Return Void")
    void testGivenUserObject_When_UpdateIsCalled_ShouldReturn_Void() {

        user.setName("Luiz");
        user.setEmail("luiz@gmail.com");

        when(repository.save(user)).thenReturn(user);
        service.update(user);
        verify(repository, atLeastOnce()).save(user);

        assertNotEquals(user.getName(), "Pedro", () -> "Name should be different");
    }
}