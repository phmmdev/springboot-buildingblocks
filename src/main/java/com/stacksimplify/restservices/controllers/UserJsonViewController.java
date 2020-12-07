package com.stacksimplify.restservices.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.entities.Views;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.View;
import javax.validation.constraints.Min;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/jsonview/users")
public class UserJsonViewController {

    @Autowired
    private UserService userService;

    @GetMapping("/internal/{id}")
    @JsonView(Views.Internal.class)
    public Optional<User> getInteralUserById(@PathVariable("id") @Min(1)Long id)
    {
        try {
            return userService.getUserById(id);
        }
        catch (UserNotFoundException ex)
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
    @GetMapping("/external/{id}")
    @JsonView(Views.External.class)
    public Optional<User> getExternalUserById(@PathVariable("id") @Min(1)Long id)
    {
        try {
            return userService.getUserById(id);
        }
        catch (UserNotFoundException ex)
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

}
