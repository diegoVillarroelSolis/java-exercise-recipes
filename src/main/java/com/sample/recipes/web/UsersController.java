package com.sample.recipes.web;

import com.sample.recipes.domain.Recipe;
import com.sample.recipes.domain.User;
import com.sample.recipes.domain.dto.RecipeDTO;
import com.sample.recipes.domain.dto.UserDTO;
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
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDTO user) {
        try {
            User newUser = usersService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.ACCEPTED);
        }
        catch (RestClientException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> users = usersService.getUsers();
            return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
        }
        catch (RestClientException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UserDTO user) {
        try {
            User updatedUser = usersService.updateUser(id, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
        }
        catch (RestClientException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
