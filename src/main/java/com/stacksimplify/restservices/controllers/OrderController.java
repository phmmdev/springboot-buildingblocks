package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.OrderNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userid}/orders")
    public List<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {
        Optional<User> optionalUser =  userRepository.findById(userid);
        if(!optionalUser.isPresent())
            throw new UserNotFoundException("User not found");

        return optionalUser.get().getOrders();
    }

    @PostMapping("/{userid}/orders")
    public Order createOrder(@PathVariable Long userid, @RequestBody Order order) throws UserNotFoundException {
        Optional<User> optionalUser =  userRepository.findById(userid);
        if(!optionalUser.isPresent())
            throw new UserNotFoundException("User not found");

        User user =  optionalUser.get();
        order.setUser(user);
        orderRepository.save(order);

        return order;
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
