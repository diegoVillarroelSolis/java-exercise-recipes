package com.sample.recipes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.recipes.controllers.models.UserDTO;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.services.RecipesService;
import com.sample.recipes.services.UsersService;
import com.sample.recipes.controllers.UsersController;
import org.junit.Assert;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @MockBean
    private RecipesService recipesService;

    private ObjectMapper mapper;

    @Before
    public void settingUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void whenGetUsers() throws Exception {
        List<UserDTO> users = Arrays.asList(
                new UserDTO("Juan", new Date(), "juan@email.com", "password"),
                new UserDTO("Jose", new Date(), "jose@email.com", "password")
        );

        when(usersService.getUsers()).thenReturn(users);

        ResultActions resultActions = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        List usersResult = mapper.readValue(resultActions.andReturn()
                .getResponse()
                .getContentAsString(), List.class);

        while(!users.iterator().hasNext() || !usersResult.iterator().hasNext()) {
            UserDTO userExpected = users.iterator().next();
            UserDTO userResult = mapper.convertValue(usersResult.iterator().next(), UserDTO.class);

            Assert.assertEquals(userExpected, userResult);
        }
    }

    @Test
    public void whenRegisterUser() throws Exception {
        UserDTO user = new UserDTO("Juan", new Date(), "juan@email.com", "password");
        when(usersService.addUser(user)).thenReturn(user);

        String userJson = mapper.writeValueAsString(user);

        ResultActions resultActions = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());

        UserDTO userResult = mapper.readValue(resultActions.andReturn()
                .getResponse()
                .getContentAsString(), UserDTO.class);

        Assert.assertEquals(user, userResult);
    }
}
