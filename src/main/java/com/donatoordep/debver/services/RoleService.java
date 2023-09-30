package com.donatoordep.debver.services;

import com.donatoordep.debver.entities.Role;
import com.donatoordep.debver.enums.RoleName;
import com.donatoordep.debver.services.exceptions.NotFoundEntityException;
import com.donatoordep.debver.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public Role findById(Long id) {
        return repository.findById(id).orElseThrow(NotFoundEntityException::new);
    }

    public List<Role> separateRolesWithHierarchy(RoleName role) {
        List<Role> listCreated = new ArrayList<>();

        Role admin = this.findById(1L);
        Role client = this.findById(2L);
        Role moderator = this.findById(3L);

        if (role == null) {
            listCreated.add(this.findById(2L));
        } else if (is(role, admin.getRoleName())) {
            listCreated.addAll(Arrays.asList(admin, client, moderator));
        } else if (is(role, moderator.getRoleName())) {
            listCreated.addAll(Arrays.asList(client, moderator));
        }
        return listCreated;
    }

    public static boolean is(RoleName role, RoleName roleName) {
        return role.equals(roleName);
    }
}
