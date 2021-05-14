package com.kiwi.services.implementation;

import com.kiwi.entities.Role;
import com.kiwi.repositories.RoleRepository;
import com.kiwi.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public Optional<Role> update(Role role, long id) {
        return roleRepository.findById(id).map(roleUpdated -> {
            roleUpdated.setName(role.getName());

            return roleRepository.save(roleUpdated);
        });
    }

    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }
}
