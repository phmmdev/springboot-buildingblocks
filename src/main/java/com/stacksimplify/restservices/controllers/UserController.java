package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNameNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser()
    {
        return  userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable("id") @Min(1)Long id)
    {
        try {
            return userService.getUserById(id);
        }
        catch (UserNotFoundException ex)
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/byusername/{name}")
    public User getUserByName(@PathVariable("name") String name) throws  UserNameNotFoundException
    {
        Optional<User> optionalUser = userService.getByUsername(name);
        if(!optionalUser.isPresent())
            throw new UserNameNotFoundException(String.format("UserName %s not found in database", name));

        return optionalUser.get();
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder)
    {
        try {
            userService.createUser(user);
            HttpHeaders headers =  new HttpHeaders();
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getUserid()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user)
    {
        try
        {
            return userService.updateUser(id, user);
        }
        catch(UserNotFoundException ex)
        {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id)
    {
        userService.deleteUser(id);
    }
}
