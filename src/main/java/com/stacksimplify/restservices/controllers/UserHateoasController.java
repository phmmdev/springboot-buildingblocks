package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNameNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/hateoas/users")
public class UserHateoasController {

    @Autowired
    private UserService userService;

    @GetMapping
    public CollectionModel<User> getAllUser() throws UserNotFoundException {
        List<User> users =  userService.getAllUsers();
        for (User user : users){
            Long userid = user.getUserid();
            Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
            user.add(selfLink);

            CollectionModel<Order> orderCollectionModel =  WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userid);
            Link orderLink =  WebMvcLinkBuilder.linkTo(orderCollectionModel).withRel("all-orders");
            user.add(orderLink);
        }
        Link selfLinkgetAllUsers =  WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
        CollectionModel<User> usercollectionModel = CollectionModel.of(users, selfLinkgetAllUsers);

        return usercollectionModel;
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable("id") @Min(1)Long id) {
        try {

            Optional<User> optionalUser =  userService.getUserById(id);
            Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(optionalUser.get().getUserid()).withSelfRel();
            User user =  optionalUser.get();
            user.add(selfLink);

            return new EntityModel<User>(user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
