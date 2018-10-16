package com.sample.recipes.controllers;

import com.sample.recipes.domain.Recipe;
import com.sample.recipes.domain.dto.RecipeDTO;
import com.sample.recipes.services.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "recipes")
public class RecipesController {
    @Autowired
    private RecipesService recipesService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Recipe> registerRecipe(@Valid @RequestBody RecipeDTO recipe) {
        try {
            Recipe newRecipe = recipesService.addRecipe(recipe);
            return new ResponseEntity<>(newRecipe, HttpStatus.ACCEPTED);
        }
        catch (RestClientException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Recipe>> getRecipes() {
        try {
            Iterable<Recipe> recipes = recipesService.getRecipes();
            return new ResponseEntity<>(recipes, HttpStatus.ACCEPTED);
        }
        catch (RestClientException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        try {
            Recipe recipe = recipesService.getRecipeById(id);
            return new ResponseEntity<>(recipe, HttpStatus.ACCEPTED);
        }
        catch (RestClientException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long id, @RequestBody RecipeDTO recipe) {
        try {
            Recipe updatedRecipe = recipesService.updateRecipe(id, recipe);
            return new ResponseEntity<>(updatedRecipe, HttpStatus.ACCEPTED);
        }
        catch (RestClientException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable long id) {
        try {
            Recipe recipe = recipesService.deleteRecipe(id);
            return new ResponseEntity<>(recipe, HttpStatus.ACCEPTED);
        }
        catch (RestClientException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
