package com.sample.recipes;


import com.sample.recipes.controllers.models.RecipeDTO;
import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.RecipesRepository;
import com.sample.recipes.persistence.entities.Recipe;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.services.RecipesService;
import com.sample.recipes.services.UsersService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipesServiceTest {
    @Mock
    private RecipesRepository recipesRepository;
    @Mock
    private UsersService usersService;
    @InjectMocks
    private RecipesService recipesService;

    @Test
    public void whenAddRecipe() throws NotFoundException {
        Recipe recipe = new Recipe("Recipe1", "Description1");
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        recipe.setUser(user);

        RecipeDTO newRecipe = new RecipeDTO("Recipe1", "Description1");

        when(usersService.getUserById(user.getId())).thenReturn(user);
        when(recipesRepository.save(recipe)).thenReturn(recipe);

        assertEquals(recipesService.addRecipe(newRecipe), newRecipe);
    }

    @Test
    public void whenAddRecipeWithEmptyData() throws NotFoundException {
        Recipe expectedRecipe = new Recipe("Recipe1", "Description1");
        RecipeDTO newEmptyRecipe = new RecipeDTO();
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        expectedRecipe.setUser(user);

        RecipeDTO expectedNewRecipe = new RecipeDTO("Recipe1", "Description1");

        when(usersService.getUserById(user.getId())).thenReturn(user);
        when(recipesRepository.save(expectedRecipe)).thenReturn(expectedRecipe);

        assertEquals(recipesService.addRecipe(newEmptyRecipe), newEmptyRecipe);
    }

    @Test
    public void whenGetRecipes() {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        Recipe recipe1 = new Recipe("Recipe1", "Description1");
        Recipe recipe2 = new Recipe("Recipe2", "Description2");
        recipe1.setUser(user);
        recipe2.setUser(user);

        RecipeDTO newRecipe1 = new RecipeDTO("Recipe1", "Description1", user.getId());
        RecipeDTO newRecipe2 = new RecipeDTO("Recipe2", "Description2", user.getId());

        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        when(recipesRepository.findAll()).thenReturn(recipes);

        List<RecipeDTO> expectedRecipes = recipesService.getRecipes();

        assertEquals(expectedRecipes.get(0), newRecipe1);
        assertEquals(expectedRecipes.get(1), newRecipe2);
    }

    @Test
    public void whenUpdateRecipe() throws NotFoundException {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        Recipe recipe = new Recipe("Recipe", "Description");
        recipe.setUser(user);
        RecipeDTO updatedRecipe = new RecipeDTO("Recipe", "Description", user.getId());
        long recipeId = 0;

        when(recipesRepository.save(recipe)).thenReturn(recipe);
        when(recipesRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        assertEquals(recipesService.updateRecipe(recipeId, updatedRecipe), updatedRecipe);
    }

    @Test
    public void whenUpdateRecipeWithEmptyData() throws NotFoundException {
        Recipe expectedRecipe = new Recipe("Recipe", "Description");
        RecipeDTO updatedEmptyRecipe = new RecipeDTO();
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        expectedRecipe.setUser(user);

        Recipe recipe = new Recipe("Recipe", "Description");
        recipe.setUser(user);
        RecipeDTO updatedRecipe = new RecipeDTO("Recipe", "Description", user.getId());
        long recipeId = 0;

        when(recipesRepository.save(recipe)).thenReturn(recipe);
        when(recipesRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        assertEquals(recipesService.updateRecipe(recipeId, updatedEmptyRecipe), updatedRecipe);
    }

    @Test
    public void whenGetRecipeById() throws NotFoundException {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        Recipe recipe = new Recipe("Recipe", "Description");
        RecipeDTO expectedRecipe = new RecipeDTO("Recipe", "Description");
        recipe.setUser(user);
        long recipeId = 0;

        when(recipesRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        assertEquals(recipesService.getRecipeById(recipeId), expectedRecipe);
    }

    @Test(expected = NotFoundException.class)
    public void whenGetRecipeByIdWithInvalidId() throws NotFoundException {
        long recipeId = -1;

        when(recipesRepository.findById(recipeId)).thenReturn(Optional.empty());

        assertEquals(recipesService.getRecipeById(recipeId), null);
    }

    @Test
    public void whenDeleteRecipe() throws NotFoundException {
        long recipeId = 0;
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        Recipe recipe = new Recipe("Recipe", "Description");
        recipe.setUser(user);

        RecipeDTO deletedRecipe = new RecipeDTO("Recipe", "Description", user.getId());

        when(recipesRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        doNothing().when(recipesRepository).delete(recipe);

        assertEquals(recipesService.deleteRecipe(recipeId), deletedRecipe);
    }

    @Test(expected = NotFoundException.class)
    public void whenDeleteRecipeWithInvalidId() throws NotFoundException {
        long recipeId = -1;
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        Recipe recipe = new Recipe("Recipe", "Description");
        recipe.setUser(user);

        when(recipesRepository.findById(recipeId)).thenReturn(Optional.empty());

        doNothing().when(recipesRepository).delete(recipe);

        assertEquals(recipesService.deleteRecipe(recipeId), null);
    }
}
