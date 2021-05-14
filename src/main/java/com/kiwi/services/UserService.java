package com.kiwi.services;

import com.kiwi.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    List<User> findAll();

    User findById(long id);

    Optional<User> update(User user, long id);

    void delete(long id);
}
