package com.stacksimplify.restservices.repositories;

import com.stacksimplify.restservices.entities.Order;

import com.stacksimplify.restservices.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
