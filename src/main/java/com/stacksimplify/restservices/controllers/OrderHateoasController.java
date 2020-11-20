package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.OrderNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hateoas/users")
public class OrderHateoasController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userid}/orders")
    public CollectionModel<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {
        Optional<User> optionalUser =  userRepository.findById(userid);
        if(!optionalUser.isPresent())
            throw new UserNotFoundException("User not found");

        return CollectionModel.of(optionalUser.get().getOrders());
    }

    @GetMapping("/{userid}/orders/{orderid}")
    public Order getOrderByOrderId(@PathVariable Long userid, @PathVariable Long orderid) throws UserNotFoundException, OrderNotFoundException {

        Optional<User> optionalUser =  userRepository.findById(userid);
        if(!optionalUser.isPresent())
            throw new UserNotFoundException("User not found");

        Optional<Order> optionalOrder =  orderRepository.findById(orderid);
        if(!optionalOrder.isPresent())
            throw new OrderNotFoundException("Order not found");

        Order order =  optionalOrder.get();
        return order;
    }
}
