package com.sample.recipes.services;

import com.sample.recipes.domain.User;
import com.sample.recipes.domain.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    private List<User> users = new ArrayList<>();

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public List<User> getUsers() {
        return users;
    }

    public User updateUser(long id, UserDTO updatedUser) {
        User user = users.get((int)id);
        if(user.getDateOfBirth() != null)
            user.setDateOfBirth(updatedUser.getDateOfBirth());
        if(!user.getEmail().isEmpty())
            user.setEmail(updatedUser.getEmail());
        if(!user.getName().isEmpty())
            user.setName(updatedUser.getName());
        if(!user.getPassword().isEmpty())
            user.setPassword(updatedUser.getPassword());
        return user;
    }
}
