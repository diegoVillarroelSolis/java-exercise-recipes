package com.sample.recipes.services;

import com.sample.recipes.domain.User;
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

    public User updateUser(long id, User user) {
        
    }
}
