package com.sample.recipes.security;

import com.sample.recipes.persistence.UsersRepository;
import com.sample.recipes.persistence.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = usersRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(email);
    }

    return org.springframework.security.core.userdetails.User
            .withUsername(email)
            .password(user.getPassword())
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
  }

}
