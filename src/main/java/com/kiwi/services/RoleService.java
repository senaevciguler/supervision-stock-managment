package com.kiwi.services;

import com.kiwi.entities.Favourite;
import com.kiwi.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role save(Role role);
    List<Role> findAll();
    Role findById(long id);
    Optional<Role> update(Role role, long id);
    void delete(long id);
}
