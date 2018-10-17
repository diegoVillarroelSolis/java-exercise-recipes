package com.sample.recipes;

import com.sample.recipes.controllers.models.UserDTO;
import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.UsersRepository;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.services.UsersService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsersServiceTest {
    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private UsersService usersService;

    @Test
    public void whenAddUser() {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        UserDTO newUser = new UserDTO("Juan", new Date(), "juan@email.com", "password");

        when(usersRepository.save(user)).thenReturn(user);
        Assert.assertEquals(usersService.addUser(newUser), newUser);
    }

    @Test
    public void whenGetUsers() {
        User userJuan = new User("Juan", new Date(), "juan@email.com", "password");
        User userJose = new User("Jose", new Date(), "jose@email.com", "password1");

        UserDTO newUserJuan = new UserDTO("Juan", new Date(), "juan@email.com", "password");
        UserDTO newUserJose = new UserDTO("Jose", new Date(), "jose@email.com", "password1");

        List<User> users = Arrays.asList(userJuan, userJose);

        when(usersRepository.findAll()).thenReturn(users);

        List<UserDTO> expectedUsers = usersService.getUsers();

        Assert.assertEquals(expectedUsers.get(0), newUserJuan);
        Assert.assertEquals(expectedUsers.get(1), newUserJose);
    }

    @Test
    public void whenUpdateUser() throws NotFoundException {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        UserDTO updatedUser = new UserDTO("Juan", new Date(), "juan@email.com", "password");
        long userId = 0;

        when(usersRepository.save(user)).thenReturn(user);
        Assert.assertEquals(usersService.updateUser(userId, updatedUser), updatedUser);
    }
}
