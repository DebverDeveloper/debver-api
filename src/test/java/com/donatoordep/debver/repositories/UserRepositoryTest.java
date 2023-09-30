package com.donatoordep.debver.repositories;

import com.donatoordep.debver.builders.UserBuilder;
import com.donatoordep.debver.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    User user;

    @BeforeEach
    void setup() {
        user = UserBuilder.builder()
                .id(1L)
                .name("Pedro")
                .email("pedro@gmail.com")
                .password("123456")
                .profile("http://img.com", "Sou o Pedro")
                .build();
    }

    @Test
    @DisplayName("Given User Object When Save Should Return User Saved")
    void testGivenUserObject_When_Save_ShouldReturn_UserSaved() {

        User user = UserBuilder.builder()
                .name("Luiz")
                .email("luiz@gmail.com")
                .password("123456")
                .profile("http://img.com", "Sou o Luiz")
                .build();

        User userSaved = repository.save(user);

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertNotNull(userSaved, () -> "User not should return null");
        assertTrue(userSaved.getId() > 0, () -> "Id can´t nullable");
    }

    @Test
    @DisplayName("Given User List When FindAll Should Return List User")
    void testGivenUserList_When_FindAll_ShouldReturn_UserList() {
        // Given / Arrange - Cenário inicial das classes (setar configurações, iniciar variaveis)
        User userTwo = UserBuilder.builder()
                .name("Luiz")
                .id(2L)
                .cart()
                .password("123456")
                .email("luiz@gmail.com")
                .profile("http://img.com", "Sou o Luiz")
                .build();

        repository.save(user);
        repository.save(userTwo);

        // When / Act- Inicia a execução do cenário.
        List<User> userList = repository.findAll();

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertNotNull(userList, () -> "User list not should return null");
        assertEquals(2, userList.size(), () -> "User list should returned 2 of length");
    }

    @Test
    @DisplayName("Given User Object When FindById Should Return User")
    void testGivenUserObject_When_FindById_ShouldReturn_UserObject() {

        repository.save(user);

        // When / Act- Inicia a execução do cenário.
        User userSearch = repository.findById(user.getId()).get();

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertNotNull(userSearch, () -> "User not should return null");
        assertEquals(user.getId(), userSearch.getId(), () -> "User should returned same id");
        assertTrue(userSearch.getId() > 0, () -> "User id is not valid");
    }

    @Test
    @DisplayName("Given User Object When FindEmailForUser Should Return User")
    void testGivenUserObject_When_FindEmailForUser_ShouldReturn_UserObject() {

        // When / Act- Inicia a execução do cenário.
        User userSearch = repository.findEmailForUser(user.getEmail()).get();

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertNotNull(userSearch, () -> "User not should return null");
        assertEquals(user.getEmail(), userSearch.getEmail(), () -> "User should returned same email");
        assertNotNull(userSearch.getEmail(), () -> "User email is not valid");
    }

    @Test
    @DisplayName("Given User Object When FindByEmailForUserDetails Should Return User")
    void testGivenUserObject_When_FindByEmailForUserDetails_ShouldReturn_UserObject() {

        // When / Act- Inicia a execução do cenário.
        UserDetails userSearch = repository.findByEmailForUserDetails(user.getEmail());

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertNotNull(userSearch, () -> "User details not should return null");
        assertNotNull(userSearch.getAuthorities(), () -> "User authority not should returned null");
        assertEquals(userSearch.getUsername(), user.getEmail(), () -> "User email is not equal");
    }

    @Test
    @DisplayName("Given User Object When FindByName Should Return User")
    void testGivenUserObject_When_FindByName_ShouldReturn_UserObject() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        // When / Act- Inicia a execução do cenário.
        Page<User> userSearch = repository.findByName(user.getName(), pageable);

        String name = userSearch.getContent().get(0).getName();

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertNotNull(userSearch, () -> "User page not should return null");
        assertEquals(name, user.getName(), () -> "User name is not equal");
    }

    @Test
    @DisplayName("Given User Object When Update Should Return User")
    void testGivenUserObject_When_Update_ShouldReturn_UserObject() {

        // When / Act- Inicia a execução do cenário.
        User userSaved = repository.findById(user.getId()).get();

        userSaved.setName("Gabriel");
        userSaved.setEmail("gabriel@gmail.com");

        User updatedUser = repository.save(userSaved);

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertNotNull(updatedUser);
        assertEquals(userSaved.getId(), updatedUser.getId(), () -> "Id is different");
        assertEquals("Gabriel", updatedUser.getName(), () -> "Name should return equals name");
        assertEquals("gabriel@gmail.com", updatedUser.getEmail(), () -> "Email should return equals email");
        assertNotNull(userSaved, () -> "User not should return null");
    }

    @Test
    @DisplayName("Given User Object When Delete Should Remove User")
    void testGivenUserObject_When_Delete_ShouldRemoveUser() {

        // When / Act- Inicia a execução do cenário.
        repository.deleteById(user.getId());

        Optional<User> userAfterDelete = repository.findById(user.getId());

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertTrue(userAfterDelete.isEmpty(), () -> "User after delete should be null");
    }
}
