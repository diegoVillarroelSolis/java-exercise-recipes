package com.sample.recipes.controllers.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class RecipeDTO {

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    private long userId;

    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public RecipeDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RecipeDTO(String name, String description, long userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    public RecipeDTO() {}

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeDTO recipeDTO = (RecipeDTO) o;
        return userId == recipeDTO.userId &&
                Objects.equals(name, recipeDTO.name) &&
                Objects.equals(description, recipeDTO.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, description, userId);
    }

    @Override
    public String toString() {
        return name + " " + description;
    }
}
