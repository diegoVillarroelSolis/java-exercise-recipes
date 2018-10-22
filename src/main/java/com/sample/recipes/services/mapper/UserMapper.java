package com.sample.recipes.services.mapper;

import com.sample.recipes.controllers.models.UserDTO;
import com.sample.recipes.persistence.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface UserMapper {

    UserDTO convertToUserDto(User entity);

    User convertToUserEntity(UserDTO dto);

}
