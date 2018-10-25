package com.sample.recipes.services.mapper;

import com.sample.recipes.controllers.models.RecipeDTO;
import com.sample.recipes.controllers.models.RecipeUpdateDTO;
import com.sample.recipes.persistence.entities.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = UserMapper.class)
public interface RecipeMapper {

    @Mapping(source = "user", target = "user")
    RecipeDTO convertToRecipeDto(Recipe entity);

    RecipeUpdateDTO convertToRecipeUpdateDto(Recipe entity);

    @Mapping(source = "user", target = "user")
    Recipe convertToRecipeEntity(RecipeDTO dto);
}
