package com.sample.recipes.persistence;

import com.sample.recipes.persistence.entities.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipesRepository extends CrudRepository<Recipe, Long> {
}
