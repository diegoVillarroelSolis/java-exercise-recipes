package com.sample.recipes.controllers.models;

import javax.validation.constraints.NotNull;

public class RecipeDTO {

    private String name;
    private String description;

    public RecipeDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @NotNull
    private long userId;

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
}
