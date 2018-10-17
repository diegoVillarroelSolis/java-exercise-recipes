package com.sample.recipes.controllers.models;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

public class UserDTO {
    @NotNull
    private String name;
    @NotNull
    private Date dateOfBirth;
    @NotNull
    private String email;
    @NotNull
    private String password;

    public UserDTO(@NotNull String name, @NotNull Date dateOfBirth, @NotNull String email, @NotNull String password) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
    }

    public UserDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(name, userDTO.name) &&
                Objects.equals(dateOfBirth, userDTO.dateOfBirth) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(password, userDTO.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, dateOfBirth, email, password);
    }
}
