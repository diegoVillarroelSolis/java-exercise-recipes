package com.sample.recipes.services;

import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.entities.Recipe;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.controllers.models.RecipeDTO;
import com.sample.recipes.persistence.RecipesRepository;
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

    public RecipeDTO addRecipe(@Valid RecipeDTO recipe) throws NotFoundException {
        Recipe newRecipe = new Recipe();
        User user = usersService.getUserById(recipe.getUserId());

        if(recipe.getName() != null)
            newRecipe.setName(recipe.getName());
        if(recipe.getDescription() != null)
            newRecipe.setDescription(recipe.getDescription());

        newRecipe.setUser(user);
        recipesRepository.save(newRecipe);
        return recipe;
    }

    public RecipeDTO updateRecipe(Long id, RecipeDTO updatedRecipe) throws NotFoundException {
        Optional<Recipe> recipe = recipesRepository.findById(id);
        Recipe recipeValue;

        if(recipe.isPresent()) {
            recipeValue = recipe.get();
        }
        else {
            throw new NotFoundException();
        }
        if(updatedRecipe.getName() != null)
            recipeValue.setName(updatedRecipe.getName());
        if(updatedRecipe.getDescription() != null)
            recipeValue.setDescription(updatedRecipe.getDescription());

        recipesRepository.save(recipeValue);

        return new RecipeDTO(recipeValue.getName(), recipeValue.getDescription());
    }

    public List<RecipeDTO> getRecipes() {
        Iterable<Recipe> recipes = recipesRepository.findAll();
        List<RecipeDTO> foundRecipes = new ArrayList<>();
        recipes.forEach(r -> foundRecipes.add(new RecipeDTO(r.getName(), r.getDescription())));
        return foundRecipes;
    }

    public RecipeDTO deleteRecipe(long id) throws NotFoundException {
        Optional<Recipe> recipe = recipesRepository.findById(id);
        RecipeDTO deletedRecipe;
        if(recipe.isPresent()) {
            recipesRepository.delete(recipe.get());
            deletedRecipe = new RecipeDTO(recipe.get().getName(), recipe.get().getDescription());
        }
        else {
            throw new NotFoundException();
        }
        return deletedRecipe;
    }

    public RecipeDTO getRecipeById(long id) throws NotFoundException {
        Optional<Recipe> recipe = recipesRepository.findById(id);
        RecipeDTO foundRecipe;
        if(recipe.isPresent()) {
            foundRecipe = new RecipeDTO(recipe.get().getName(), recipe.get().getDescription());
        }
        else {
            throw new NotFoundException();
        }
        return foundRecipe;
    }
}
