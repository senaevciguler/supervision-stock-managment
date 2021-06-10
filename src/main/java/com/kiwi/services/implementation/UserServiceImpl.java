package com.kiwi.services.implementation;

import com.kiwi.entities.User;
import com.kiwi.exception.NotFoundException;
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
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user can not find with id :" + id));
    }

    @Override
    public Optional<User> update(User user, long id) {
        return userRepository.findById(id).map(userUpdated -> {
            userUpdated.setAddress(user.getAddress());
            userUpdated.setEmail(user.getEmail());
            userUpdated.setFavourites(user.getFavourites());
            userUpdated.setFirstName(user.getFirstName());
            userUpdated.setUsername(user.getUsername());
            userUpdated.setLastName(user.getLastName());
            userUpdated.setOrders(user.getOrders());
            userUpdated.setPassword(user.getPassword());
            userUpdated.setRole(user.getRole());

            return userRepository.save(userUpdated);
        });
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
