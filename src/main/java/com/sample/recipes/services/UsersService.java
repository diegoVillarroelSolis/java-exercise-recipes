package com.sample.recipes.services;

import com.sample.recipes.domain.User;
import com.sample.recipes.domain.dto.UserDTO;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    private List<User> users = new ArrayList<>();

    public User addUser(@Valid UserDTO user) {
        User newUser = new User();
        newUser.setDateOfBirth(user.getDateOfBirth());
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        users.add(newUser);
        return newUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public User updateUser(long id, UserDTO updatedUser) {
        User user = users.get((int)id);
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        return user;
    }

    public User getUserById(long userId) {
        if(existsUser(userId))
            return users.get((int) userId);
        return null;
    }

    private boolean existsUser(long userId) {
        return userId >= 0 && userId < users.size();
    }
}
