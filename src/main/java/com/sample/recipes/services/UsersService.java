package com.sample.recipes.services;

import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.controllers.models.UserDTO;
import com.sample.recipes.persistence.UsersRepository;
import com.sample.recipes.utils.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersService {

    private static final String USER_NOT_FOUND = "The user specified was not found.";
    private static final String RECIPE_NOT_FOUND = "The recipe ID specified was not found.";

    @Autowired
    private UsersRepository usersRepository;

    public UserDTO addUser(@Valid UserDTO user) {
        User savedUser;
        UserDTO responseUser = null;

        if( checkUserParameters(user) ) {
            User newUserEntity = MapperHelper.USER_MAPPER.convertToUserEntity(user);
            savedUser = usersRepository.save(newUserEntity);
            responseUser = MapperHelper.USER_MAPPER.convertToUserDto(savedUser);
        }

        return responseUser;
    }

    public List<UserDTO> getUsers() {
        return StreamSupport.stream(usersRepository.findAll().spliterator(), false)
                .map(MapperHelper.USER_MAPPER::convertToUserDto)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(long id, UserDTO updatedUser) throws NotFoundException {
        Optional<User> user = usersRepository.findById(id);
        User foundUser;
        UserDTO responseUser = null;

        if(user.isPresent()) {
            foundUser = user.get();
        }
        else {
            throw new NotFoundException(USER_NOT_FOUND);
        }

        if( checkUserParameters(updatedUser) ) {
            foundUser.setDateOfBirth(updatedUser.getDateOfBirth());
            foundUser.setEmail(updatedUser.getEmail());
            foundUser.setName(updatedUser.getName());
            foundUser.setPassword(updatedUser.getPassword());

            usersRepository.save(foundUser);
            responseUser = MapperHelper.USER_MAPPER.convertToUserDto(foundUser);
        }

        return responseUser;
    }

    public UserDTO getUserById(long userId) throws NotFoundException {
        Optional<User> user = usersRepository.findById(userId);
        User foundUser;

        if(user.isPresent()) {
            foundUser = user.get();
        }
        else {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return MapperHelper.USER_MAPPER.convertToUserDto(foundUser);
    }

    public User findUserById(long userId) throws NotFoundException {
        Optional<User> user = usersRepository.findById(userId);
        User foundUser;

        if(user.isPresent()) {
            foundUser = user.get();
        }
        else {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return foundUser;
    }

    private boolean checkStringValue (String s) {
        return s != null && !s.isEmpty();
    }

    private boolean checkUserParameters(UserDTO user) {
        return checkStringValue(user.getName())
                && user.getDateOfBirth() != null
                && checkStringValue(user.getEmail())
                && checkStringValue(user.getPassword());
    }

    public UserDTO deleteUser(long id) throws NotFoundException {
        Optional<User> user = usersRepository.findById(id);
        UserDTO deletedRecipe;
        if(user.isPresent()) {
            usersRepository.delete(user.get());
            deletedRecipe = MapperHelper.USER_MAPPER.convertToUserDto(user.get());
        }
        else {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return deletedRecipe;
    }
}
