package com.sample.recipes;

import com.sample.recipes.controllers.models.UserDTO;
import com.sample.recipes.persistence.UsersRepository;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.services.UsersService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.mockito.Mockito.when;

public class UsersServiceTest {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;

    @Test
    public void whenAddUser() {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        UserDTO newUser = new UserDTO("Juan", new Date(), "juan@email.com", "password");
        when(usersRepository.save(user)).thenReturn(user);
        Assert.assertEquals(usersService.addUser());
    }
}
