package com.sample.recipes.persistence;

import com.sample.recipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipesRepository extends CrudRepository<Recipe, Long> {
}
