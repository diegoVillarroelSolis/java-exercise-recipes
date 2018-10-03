package com.sample.recipes.persistence;

import com.sample.recipes.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
}
