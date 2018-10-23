package com.sample.recipes.services;

import com.sample.recipes.controllers.models.UserDTO;
import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.entities.Recipe;
import com.sample.recipes.controllers.models.RecipeDTO;
import com.sample.recipes.persistence.RecipesRepository;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.utils.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipesService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RecipesRepository recipesRepository;

    public RecipeDTO addRecipe(@Valid RecipeDTO recipe) throws NotFoundException {
        Recipe newRecipe;
        RecipeDTO responseRecipe = null;
        User user = usersService.finUserById(recipe.getUserId());

        if(checkRecipeParameters(recipe)) {
            newRecipe = MapperHelper.RECIPE_MAPPER.convertToRecipeEntity(recipe);
            newRecipe.setUser(user);
            recipesRepository.save(newRecipe);
            responseRecipe = MapperHelper.RECIPE_MAPPER.convertToRecipeDto(newRecipe);
        }
        return responseRecipe;
    }

    public RecipeDTO updateRecipe(Long id, RecipeDTO updatedRecipe) throws NotFoundException {

        usersService.finUserById(updatedRecipe.getUserId());
        Optional<Recipe> recipe = recipesRepository.findById(id);
        Recipe recipeValue;
        RecipeDTO responseRecipe = null;

        if(recipe.isPresent()) {
            recipeValue = recipe.get();
        }
        else {
            throw new NotFoundException();
        }

        if( checkRecipeParameters(updatedRecipe) ) {
            recipeValue.setName(updatedRecipe.getName());
            recipeValue.setDescription(updatedRecipe.getDescription());
            recipesRepository.save(recipeValue);
            responseRecipe = MapperHelper.RECIPE_MAPPER.convertToRecipeDto(recipeValue);
        }



        return responseRecipe;
    }

    public List<RecipeDTO> getRecipes() {
        return StreamSupport.stream(recipesRepository.findAll().spliterator(), false)
                .map(MapperHelper.RECIPE_MAPPER::convertToRecipeDto)
                .collect(Collectors.toList());
    }

    public RecipeDTO deleteRecipe(long id) throws NotFoundException {
        Optional<Recipe> recipe = recipesRepository.findById(id);
        RecipeDTO deletedRecipe;
        if(recipe.isPresent()) {
            recipesRepository.delete(recipe.get());
            deletedRecipe = MapperHelper.RECIPE_MAPPER.convertToRecipeDto(recipe.get());
        }
        else {
            throw new NotFoundException();
        }
        return deletedRecipe;
    }

    public RecipeDTO getRecipeById(long id) throws NotFoundException {
        Optional<Recipe> recipe = recipesRepository.findById(id);
        Recipe foundRecipe;
        if(recipe.isPresent()) {
            foundRecipe = recipe.get();
        }
        else {
            throw new NotFoundException();
        }
        return MapperHelper.RECIPE_MAPPER.convertToRecipeDto(foundRecipe);
    }

    private boolean checkStringValue (String s) {
        return s != null && !s.isEmpty();
    }

    private boolean checkRecipeParameters(RecipeDTO recipe) {
        return checkStringValue(recipe.getName())
                && checkStringValue(recipe.getDescription());
    }
}
