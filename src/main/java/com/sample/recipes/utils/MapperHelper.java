package com.sample.recipes.utils;

import com.sample.recipes.services.mapper.RecipeMapper;
import com.sample.recipes.services.mapper.UserMapper;
import org.mapstruct.factory.Mappers;

public abstract class MapperHelper {

    public static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    public static final RecipeMapper RECIPE_MAPPER = Mappers.getMapper(RecipeMapper.class);

}