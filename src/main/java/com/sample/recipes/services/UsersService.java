package com.sample.recipes.services;

import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.controllers.models.UserDTO;
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

    public UserDTO addUser(@Valid UserDTO user) {
        User newUser = new User();

        if( user.getName() != null || user.getDateOfBirth() != null || user.getEmail()!= null || user.getPassword()!= null ) {
            newUser.setDateOfBirth(user.getDateOfBirth());
            newUser.setEmail(user.getEmail());
            newUser.setName(user.getName());
            newUser.setPassword(user.getPassword());
            usersRepository.save(newUser);
        }
        else {
            user = null;
        }

        return user;
    }

    public List<UserDTO> getUsers() {
        Iterable<User> users = usersRepository.findAll();
        List<UserDTO> foundUsers = new ArrayList<>();
        users.forEach(u -> foundUsers.add(new UserDTO(u.getName(), u.getDateOfBirth(), u.getEmail(), u.getPassword())));
        return foundUsers;
    }

    public UserDTO updateUser(long id, UserDTO updatedUser) throws NotFoundException {
        Optional<User> user = usersRepository.findById(id);
        User foundUser;

        if(user.isPresent()) {
            foundUser = user.get();
        }
        else {
            throw new NotFoundException();
        }

        if( updatedUser.getName() != null || updatedUser.getDateOfBirth() != null || updatedUser.getEmail()!= null || updatedUser.getPassword()!= null ) {
            foundUser.setDateOfBirth(updatedUser.getDateOfBirth());
            foundUser.setEmail(updatedUser.getEmail());
            foundUser.setName(updatedUser.getName());
            foundUser.setPassword(updatedUser.getPassword());
            usersRepository.save(foundUser);
        }
        else {
            updatedUser = null;
        }

        return updatedUser;
    }

    public User getUserById(long userId) throws NotFoundException {
        Optional<User> user = usersRepository.findById(userId);
        User foundUser;

        if(user.isPresent()) {
            foundUser = user.get();
        }
        else {
            throw new NotFoundException();
        }
        return foundUser;
    }
}
