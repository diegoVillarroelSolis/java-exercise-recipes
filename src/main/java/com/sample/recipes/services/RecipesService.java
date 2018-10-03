package com.sample.recipes.services;

import com.sample.recipes.domain.Recipe;
import com.sample.recipes.domain.User;
import com.sample.recipes.domain.dto.RecipeDTO;
import com.sample.recipes.persistence.RecipesRepository;
import com.sample.recipes.persistence.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipesService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RecipesRepository recipesRepository;

    public Recipe addRecipe(@Valid RecipeDTO recipe) {
        Recipe newRecipe = new Recipe();
        User user = usersService.getUserById(recipe.getUserId());
        if(!recipe.getName().isEmpty())
            newRecipe.setName(recipe.getName());
        if(!recipe.getDescription().isEmpty())
            newRecipe.setDescription(recipe.getDescription());
        newRecipe.setUser(user);
        return recipesRepository.save(newRecipe);
    }

    public Recipe updateRecipe(Long id, RecipeDTO updatedRecipe) {
        Recipe recipe = recipesRepository.findById(id).get();
        if(!recipe.getName().isEmpty())
            recipe.setName(updatedRecipe.getName());
        if(!recipe.getDescription().isEmpty())
            recipe.setDescription(updatedRecipe.getDescription());
        return recipesRepository.save(recipe);
    }

    public Iterable<Recipe> getRecipes() {
        return recipesRepository.findAll();
    }

    public Recipe deleteRecipe(long id) {
        Optional<Recipe> recipe = recipesRepository.findById(id);
        Recipe recipeValue = null;
        if(recipe.isPresent()) {
            recipesRepository.delete(recipe.get());
            recipeValue = recipe.get();
        }
        return recipeValue;
    }

    public Recipe getRecipeById(long id) {
        Optional<Recipe> recipe = recipesRepository.findById(id);
        Recipe recipeValue = null;
        if(recipe.isPresent()) {
            recipeValue = recipe.get();
        }
        return recipeValue;
    }
}
