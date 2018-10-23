package com.sample.recipes.persistence;

import com.sample.recipes.persistence.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

}
