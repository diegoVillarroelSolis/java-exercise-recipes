package com.sample.recipes;

import com.sample.recipes.controllers.models.RecipeDTO;
import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.RecipesRepository;
import com.sample.recipes.persistence.entities.Recipe;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.services.RecipesService;
import com.sample.recipes.services.UsersService;
import com.sample.recipes.utils.MapperHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
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

        RecipeDTO newRecipe = MapperHelper.RECIPE_MAPPER.convertToRecipeDto(recipe);

        when(usersService.finUserById(user.getId())).thenReturn(user);
        when(recipesRepository.save(recipe)).thenReturn(recipe);

        System.out.println("Recipe1 "+ newRecipe);
        System.out.println("Recipe2 " + recipesService.addRecipe(newRecipe));

        assertEquals(recipesService.addRecipe(newRecipe), newRecipe);
    }

    @Test
    public void whenAddRecipeWithEmptyData() throws NotFoundException {
        Recipe expectedRecipe = new Recipe("Recipe1", "Description1");
        RecipeDTO newEmptyRecipe = new RecipeDTO();
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        expectedRecipe.setUser(user);

        when(usersService.finUserById(user.getId())).thenReturn(user);
        when(recipesRepository.save(expectedRecipe)).thenReturn(expectedRecipe);

        assertEquals(recipesService.addRecipe(newEmptyRecipe), null);
    }

    @Test
    public void whenGetRecipes() {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        Recipe recipe1 = new Recipe("Recipe1", "Description1");
        Recipe recipe2 = new Recipe("Recipe2", "Description2");
        recipe1.setUser(user);
        recipe2.setUser(user);

        RecipeDTO newRecipe1 = MapperHelper.RECIPE_MAPPER.convertToRecipeDto(recipe1);
        RecipeDTO newRecipe2 = MapperHelper.RECIPE_MAPPER.convertToRecipeDto(recipe2);

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
        RecipeDTO updatedRecipe = MapperHelper.RECIPE_MAPPER.convertToRecipeDto(recipe);
        long recipeId = 0;

        when(recipesRepository.save(recipe)).thenReturn(recipe);
        when(recipesRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        assertEquals(recipesService.updateRecipe(recipeId, updatedRecipe), updatedRecipe);
    }

    @Test
    public void whenUpdateRecipeWithEmptyData() throws NotFoundException {
        RecipeDTO updatedEmptyRecipe = new RecipeDTO();
        RecipeDTO toUpdateRecipe = new RecipeDTO("Recipe", "Description");
        Recipe recipe = MapperHelper.RECIPE_MAPPER.convertToRecipeEntity(toUpdateRecipe);
        long recipeId = 0;

        when(recipesRepository.save(recipe)).thenReturn(null);
        when(recipesRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        assertEquals(recipesService.updateRecipe(recipeId, updatedEmptyRecipe), null);
    }

    @Test
    public void whenGetRecipeById() throws NotFoundException {
        User user = new User("Juan", new Date(), "juan@email.com", "password");
        Recipe recipe = new Recipe("Recipe", "Description");
        RecipeDTO expectedRecipe = MapperHelper.RECIPE_MAPPER.convertToRecipeDto(recipe);
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

        RecipeDTO deletedRecipe = MapperHelper.RECIPE_MAPPER.convertToRecipeDto(recipe);

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
