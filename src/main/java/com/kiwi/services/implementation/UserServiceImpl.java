package com.kiwi.services.implementation;

import com.kiwi.entities.User;
import com.kiwi.repositories.UserRepository;
import com.kiwi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Optional<User> update(User user, long id) {
        return Optional.empty();
    }

    @Override
    public void delete(long id) {
        //TODO write delete method
    }
}
