package com.sample.recipes.controllers;

import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.controllers.models.UserDTO;
import com.sample.recipes.services.RecipesService;
import com.sample.recipes.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RecipesService recipesService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO user) {
        UserDTO newUser = usersService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = usersService.getUsers();
        if (users.isEmpty() )
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> updateUser(@PathVariable long id, @Valid @RequestBody UserDTO user) throws NotFoundException {
        UserDTO updatedUser = usersService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable long id) throws NotFoundException {
        UserDTO updatedUser = usersService.deleteUser(id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
