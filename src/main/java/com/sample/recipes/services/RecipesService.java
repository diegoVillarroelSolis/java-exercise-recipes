package com.sample.recipes.services;

import com.sample.recipes.domain.Recipe;
import com.sample.recipes.domain.User;
import com.sample.recipes.domain.dto.RecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipesService {
    @Autowired
    private UsersService usersService;

    private List<Recipe> recipes = new ArrayList<Recipe>();

    public Recipe addRecipe(@Valid RecipeDTO recipe) {
        Recipe newRecipe = new Recipe();
        User user = usersService.getUserById(recipe.getUserId());
        if(!recipe.getName().isEmpty())
            newRecipe.setName(recipe.getName());
        if(!recipe.getDescription().isEmpty())
            newRecipe.setDescription(recipe.getDescription());
        newRecipe.setUser(user);
        recipes.add(newRecipe);
        return newRecipe;
    }

    public Recipe updateRecipe(long userId, RecipeDTO updatedRecipe) {
        Recipe recipe = recipes.get((int)userId);
        if(!recipe.getName().isEmpty())
            recipe.setName(updatedRecipe.getName());
        if(!recipe.getDescription().isEmpty())
            recipe.setDescription(updatedRecipe.getDescription());
        return recipe;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public Recipe deleteRecipe(long id) {
        if(existsRecipe(id)){
            return recipes.remove((int)id);
        }
        return null;
    }

    private boolean existsRecipe(long id) {
        return id >= 0 && id < recipes.size();
    }

    public Recipe getRecipeById(long id) {
        if(existsRecipe(id)){
            return recipes.get((int)id);
        }
        return null;
    }
}
