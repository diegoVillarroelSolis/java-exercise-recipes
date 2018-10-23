package com.sample.recipes.services;

import com.sample.recipes.exception.CustomException;
import com.sample.recipes.exception.NotFoundException;
import com.sample.recipes.persistence.entities.User;
import com.sample.recipes.controllers.models.UserDTO;
import com.sample.recipes.persistence.UsersRepository;
import com.sample.recipes.security.JwtTokenProvider;
import com.sample.recipes.utils.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyList;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public UserDTO addUser(@Valid UserDTO user) {

        User savedUser;
        UserDTO responseUser = null;

        if( checkUserParameters(user) ) {
            User newUserEntity = MapperHelper.USER_MAPPER.convertToUserEntity(user);
            newUserEntity.setPassword(passwordEncoder.encode(user.getPassword()));
            savedUser = usersRepository.save(newUserEntity);
            responseUser = MapperHelper.USER_MAPPER.convertToUserDto(savedUser);
        }

        return responseUser;
    }

    public List<UserDTO> getUsers() {
        return StreamSupport.stream(usersRepository.findAll().spliterator(), false)
                .map(MapperHelper.USER_MAPPER::convertToUserDto)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(long id, UserDTO updatedUser) throws NotFoundException {
        Optional<User> user = usersRepository.findById(id);
        User foundUser;
        UserDTO responseUser = null;

        if(user.isPresent()) {
            foundUser = user.get();
        }
        else {
            throw new NotFoundException();
        }

        if( checkUserParameters(updatedUser) ) {
            foundUser.setDateOfBirth(updatedUser.getDateOfBirth());
            foundUser.setEmail(updatedUser.getEmail());
            foundUser.setName(updatedUser.getName());
            foundUser.setPassword(updatedUser.getPassword());

            usersRepository.save(foundUser);
            responseUser = MapperHelper.USER_MAPPER.convertToUserDto(foundUser);
        }

        return responseUser;
    }

    public UserDTO getUserById(long userId) throws NotFoundException {
        Optional<User> user = usersRepository.findById(userId);
        User foundUser;

        if(user.isPresent()) {
            foundUser = user.get();
        }
        else {
            throw new NotFoundException();
        }
        return MapperHelper.USER_MAPPER.convertToUserDto(foundUser);
    }

    public User finUserById(long userId) throws NotFoundException {
        Optional<User> user = usersRepository.findById(userId);
        User foundUser;

        if(user.isPresent()) {
            foundUser = user.get();
        }
        else {
            throw new NotFoundException();
        }
        return foundUser;
    }

    private boolean checkStringValue (String s) {
        return s != null && !s.isEmpty();
    }

    private boolean checkUserParameters(UserDTO user) {
        return checkStringValue(user.getName())
                && user.getDateOfBirth() != null
                && checkStringValue(user.getEmail())
                && checkStringValue(user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User applicationUser = usersRepository.findByEmail(email);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList());
    }

    public String signin(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

//    public String signin(String username, String password) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
//        } catch (AuthenticationException e) {
//            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//    }
//
//    public String signup(User user) {
//        //if (!usersRepository.existsByUsername(user.getUsername())) {
//        if (!usersRepository.existsByEmail(user.getEmail())) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            usersRepository.save(user);
//            return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
//        } else {
//            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//    }
}
