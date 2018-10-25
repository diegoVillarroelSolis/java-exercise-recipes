package com.sample.recipes;

import com.sample.recipes.controllers.models.UserDTO;
import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.UsersRepository;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.services.UsersService;
import com.sample.recipes.utils.MapperHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UsersServiceTest {
    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private UsersService usersService;

    @Test
    public void whenAddUser() {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        UserDTO newUser = MapperHelper.USER_MAPPER.convertToUserDto(user);

        when(usersRepository.save(user)).thenReturn(user);
        Assert.assertEquals(usersService.addUser(newUser), newUser);
    }

    @Test
    public void whenAddUserWithEmptyData() {
        User expectedUser = new User("Jose", new Date(), "jose@email.com", "password");
        UserDTO newEmptyUser = new UserDTO();

        when(usersRepository.save(expectedUser)).thenReturn(expectedUser);

        assertEquals(usersService.addUser(newEmptyUser), null);
    }

    @Test
    public void whenGetUsers() {
        User userJuan = new User("Juan", new Date(), "juan@email.com", "password");
        User userJose = new User("Jose", new Date(), "jose@email.com", "password1");

        UserDTO newUserJuan = MapperHelper.USER_MAPPER.convertToUserDto(userJuan);
        UserDTO newUserJose = MapperHelper.USER_MAPPER.convertToUserDto(userJose);

        List<User> users = Arrays.asList(userJuan, userJose);

        when(usersRepository.findAll()).thenReturn(users);

        List<UserDTO> expectedUsers = usersService.getUsers();

        Assert.assertEquals(expectedUsers.get(0), newUserJuan);
        Assert.assertEquals(expectedUsers.get(1), newUserJose);
    }

    @Test
    public void whenUpdateUser() throws NotFoundException {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        UserDTO updatedUser = MapperHelper.USER_MAPPER.convertToUserDto(user);
        long userId = 0;

        when(usersRepository.save(user)).thenReturn(user);
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        Assert.assertEquals(usersService.updateUser(userId, updatedUser), updatedUser);
    }

    @Test
    public void whenUpdateUserWithEmptyData() throws NotFoundException {
        UserDTO updatedEmptyUser = new UserDTO();
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        long userId = 0;

        when(usersRepository.save(user)).thenReturn(user);
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        Assert.assertEquals(usersService.updateUser(userId, updatedEmptyUser), null);
    }

    @Test
    public void whenGetUserById() throws NotFoundException {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        long userId = 0;

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        Assert.assertEquals(usersService.findUserById(userId), user);
    }

    @Test(expected = NotFoundException.class)
    public void whenGetUserByIdWithInvalidId() throws NotFoundException {
        long userId = -1;

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        Assert.assertEquals(usersService.getUserById(userId), null);
    }

    @Test
    public void whenDeleteRecipe() throws NotFoundException {
        long userId = 0;
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        //User user = new User("User", "Description");

        UserDTO deletedUser = MapperHelper.USER_MAPPER.convertToUserDto(user);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(usersRepository).delete(user);

        assertEquals(usersService.deleteUser(userId), deletedUser);
    }

    @Test(expected = NotFoundException.class)
    public void whenDeleteUserWithInvalidId() throws NotFoundException {
        long userId = -1;
        User user = new User("Juan", new Date(), "juan@email.com", "password");

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        doNothing().when(usersRepository).delete(user);

        assertEquals(usersService.deleteUser(userId), null);
    }
}
