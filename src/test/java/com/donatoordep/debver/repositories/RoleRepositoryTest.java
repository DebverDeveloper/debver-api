package com.donatoordep.debver.repositories;

import com.donatoordep.debver.entities.Role;
import com.donatoordep.debver.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RoleRepositoryTest {
    @Autowired
    RoleRepository repository;

    Role role;

    @BeforeEach
    void setup() {
        role = new Role(1L, RoleName.ROLE_ADMIN);
    }

    @Test
    @DisplayName("Given Role Object When Save Should Return Role Saved")
    void testGivenRoleObject_When_Save_ShouldReturn_RoleSaved() {

        Role roleSaved = repository.save(role);

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertNotNull(roleSaved, () -> "Role not should return null");
        assertTrue(roleSaved.getId() > 0, () -> "Id can´t nullable");
    }

    @Test
    @DisplayName("Given Role List When FindAll Should Return List Role")
    void testGivenRoleList_When_FindAll_ShouldReturn_RoleList() {
        // Given / Arrange - Cenário inicial das classes (setar configurações, iniciar variaveis)
        Role role = new Role(1L, RoleName.ROLE_ADMIN);
        Role roleTwo = new Role(2L, RoleName.ROLE_CLIENT);

        repository.save(role);
        repository.save(roleTwo);

        // When / Act- Inicia a execução do cenário.
        List<Role> roleList = repository.findAll();

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertNotNull(roleList, () -> "Role list not should return null");
        assertEquals(3, roleList.size(), () -> "Role list should returned 2 of length");
    }

    @Test
    @DisplayName("Given Role Object When FindById Should Return Role")
    void testGivenRoleObject_When_FindById_ShouldReturn_RoleObject() {

        Role saved = repository.save(role);

        // When / Act- Inicia a execução do cenário.
        Role roleSearch = repository.findById(saved.getId()).orElse(null);

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertNotNull(roleSearch, () -> "Role not should return null");
        assertEquals(saved.getId(), roleSearch.getId(), () -> "Role should returned same id");
        assertTrue(saved.getId() > 0, () -> "Role id is not valid");
    }

    @Test
    @DisplayName("Given Role Object When Delete Should Remove Role")
    void testGivenRoleObject_When_Delete_ShouldRemoveRole() {

        repository.save(role);

        // When / Act- Inicia a execução do cenário.
        repository.deleteById(role.getId());

        Optional<Role> roleAfterDelete = repository.findById(role.getId());

        // Then / Assert - Avaliação do resultado, verifica se corresponde ao esperado.
        assertTrue(roleAfterDelete.isEmpty(), () -> "Role after delete should be null");
    }
}
