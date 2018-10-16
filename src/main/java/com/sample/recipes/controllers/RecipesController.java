package com.sample.recipes.controllers;

import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.entities.Recipe;
import com.sample.recipes.controllers.models.RecipeDTO;
import com.sample.recipes.services.RecipesService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "recipes")
public class RecipesController {
    @Autowired
    private RecipesService recipesService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RecipeDTO> registerRecipe(@Valid @RequestBody RecipeDTO recipe) throws NotFoundException {
        RecipeDTO newRecipe = recipesService.addRecipe(recipe);
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RecipeDTO>> getRecipes() {
        List<RecipeDTO> recipes = recipesService.getRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable long id) throws NotFoundException {
        RecipeDTO recipe = recipesService.getRecipeById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable long id, @RequestBody RecipeDTO recipe) throws NotFoundException {
        RecipeDTO updatedRecipe = recipesService.updateRecipe(id, recipe);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RecipeDTO> deleteRecipe(@PathVariable long id) throws NotFoundException {
        RecipeDTO recipe = recipesService.deleteRecipe(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }
}
