package com.sample.recipes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.recipes.controllers.RecipesController;
import com.sample.recipes.controllers.models.RecipeDTO;
import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.entities.Recipe;
import com.sample.recipes.services.RecipesService;
import org.junit.Assert;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RecipesController.class)
public class RecipesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipesService recipesService;

    private ObjectMapper mapper;

    @Before
    public void settingUp() {

        mapper = new ObjectMapper();
    }

    @Test
    public void whenGetRecipes() throws Exception {

        long userId = 0;

        List<RecipeDTO> recipes = Arrays.asList(
                new RecipeDTO("Recipe1", "Description1", userId),
                new RecipeDTO("Recipe1", "Description2", userId)
        );

        when(recipesService.getRecipes()).thenReturn(recipes);

        ResultActions resultActions = mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        List recipesResult = mapper.readValue(resultActions.andReturn()
                .getResponse()
                .getContentAsString(), List.class);

        while(!recipes.iterator().hasNext() || !recipesResult.iterator().hasNext()) {
            RecipeDTO recipeExpected = recipes.iterator().next();
            RecipeDTO recipeResult = mapper.convertValue(recipesResult.iterator().next(), RecipeDTO.class);

            Assert.assertEquals(recipeExpected, recipeResult);
        }
    }

    @Test
    public void whenGetRecipe() throws Exception {

        long recipeId = 0;
        RecipeDTO recipe = new RecipeDTO("Recipe1", "Description1");

        when(recipesService.getRecipeById(recipeId)).thenReturn(recipe);

        ResultActions resultActions = mockMvc.perform(get(String.format("/recipes/%d", recipeId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        RecipeDTO recipeResult = mapper.readValue(resultActions.andReturn()
                .getResponse()
                .getContentAsString(), RecipeDTO.class);

        Assert.assertEquals(recipe, recipeResult);
    }

    @Test
    public void whenGetRecipeWithInvalidId() throws Exception {

        long recipeId = -1;
        RecipeDTO recipe = new RecipeDTO("Recipe1", "Description1");

        when(recipesService.getRecipeById(recipeId)).thenThrow(NotFoundException.class);

        mockMvc.perform(get(String.format("/recipes/%d", recipeId)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenRegisterRecipe() throws Exception {

        RecipeDTO recipe = new RecipeDTO("Recipe1", "Description1");

        when(recipesService.addRecipe(recipe)).thenReturn(recipe);

        String recipeJson = mapper.writeValueAsString(recipe);

        ResultActions resultActions = mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(recipeJson)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());

        RecipeDTO recipeResult = mapper.readValue(resultActions.andReturn()
                .getResponse()
                .getContentAsString(), RecipeDTO.class);

        Assert.assertEquals(recipe, recipeResult);
    }

    @Test
    public void whenRegisterRecipeWithInvalidEmptyData() throws Exception {

        RecipeDTO recipe = new RecipeDTO();

        when(recipesService.addRecipe(recipe)).thenReturn(null);

        String recipeJson = mapper.writeValueAsString(recipe);

        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(recipeJson)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenRegisterRecipeWithInvalidUserId() throws Exception {

        long userId = -1;
        RecipeDTO recipe = new RecipeDTO("Recipe1", "Description1", userId);

        when(recipesService.addRecipe(recipe)).thenThrow(NotFoundException.class);

        String recipeJson = mapper.writeValueAsString(recipe);

        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(recipeJson)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenUpdateRecipe() throws Exception { //check logic is correct the same at whenUpdaateUser

        long recipeId = 0;
        RecipeDTO updatedRecipe = new RecipeDTO("Recipe2", "Description2");

        when(recipesService.updateRecipe(recipeId, updatedRecipe)).thenReturn(updatedRecipe);

        String recipeUpdatedJson = mapper.writeValueAsString(updatedRecipe);

        ResultActions resultActions = mockMvc.perform(put(String.format("/recipes/%d", recipeId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(recipeUpdatedJson)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        RecipeDTO recipeUpdatedResult = mapper.readValue(resultActions.andReturn()
                .getResponse()
                .getContentAsString(), RecipeDTO.class);

        Assert.assertEquals(updatedRecipe, recipeUpdatedResult);
    }

    @Test
    public void whenUpdateRecipeWithInvalidId() throws Exception {

        long recipeId = -1;
        RecipeDTO updatedRecipe = new RecipeDTO("Recipe2", "Description2");

        when(recipesService.updateRecipe(recipeId, updatedRecipe)).thenThrow(NotFoundException.class);

        String recipeUpdatedJson = mapper.writeValueAsString(updatedRecipe);

        ResultActions resultActions = mockMvc.perform(put(String.format("/recipes/%d", recipeId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(recipeUpdatedJson)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteRecipe() throws Exception {

        long recipeId = 0;
        RecipeDTO deletedRecipe = new RecipeDTO("Recipe1", "Description1");

        when(recipesService.deleteRecipe(recipeId)).thenReturn(deletedRecipe);

        String recipeDeletedJson = mapper.writeValueAsString(deletedRecipe);

        ResultActions resultActions = mockMvc.perform(delete(String.format("/recipes/%d", recipeId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(recipeDeletedJson)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        RecipeDTO recipeDeletedResult = mapper.readValue(resultActions.andReturn()
                .getResponse()
                .getContentAsString(), RecipeDTO.class);

        Assert.assertEquals(deletedRecipe, recipeDeletedResult);
    }

    @Test
    public void whenDeleteRecipeWithInvalidId() throws Exception {

        long recipeId = -1;
        RecipeDTO deletedRecipe = new RecipeDTO("Recipe1", "Description1");

        when(recipesService.deleteRecipe(recipeId)).thenThrow(NotFoundException.class);

        String recipeDeletedJson = mapper.writeValueAsString(deletedRecipe);

        ResultActions resultActions = mockMvc.perform(delete(String.format("/recipes/%d", recipeId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(recipeDeletedJson)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isNotFound());
    }
}
