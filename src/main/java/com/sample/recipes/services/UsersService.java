package com.sample.recipes.services;

import com.sample.recipes.domain.User;
import com.sample.recipes.domain.dto.UserDTO;
import com.sample.recipes.persistence.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public User addUser(@Valid UserDTO user) {
        User newUser = new User();
        newUser.setDateOfBirth(user.getDateOfBirth());
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        usersRepository.save(newUser);
        return newUser;
    }

    public Iterable<User> getUsers() {
        return usersRepository.findAll();
    }

    public User updateUser(long id, UserDTO updatedUser) {
        User user = usersRepository.findById(id).get();
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        usersRepository.save(user);
        return user;
    }

    public User getUserById(long userId) {
        Optional<User> user = usersRepository.findById(userId);
        User userValue = null;
        if(user.isPresent()) {
            userValue = user.get();
        }
        return userValue;
    }
}
