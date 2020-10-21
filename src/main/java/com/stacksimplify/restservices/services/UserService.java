package com.stacksimplify.restservices.services;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User createUser(User user) throws UserExistsException
    {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if(!optionalUser.isPresent())
            userRepository.save(user);

        throw new UserExistsException(String.format("the user name %s is already in use", user.getUsername()));
    }

    public Optional<User> getUserById(Long id) throws UserNotFoundException
    {
        Optional<User> user =  userRepository.findById(id);;
        if(!user.isPresent())
            throw  new UserNotFoundException("User Not Found in Data Base");

        return user;
    }

    public Optional<User> getByUsername(String name)
    {
        Optional<User> optionalUser =  userRepository.findByUsername(name);
        return optionalUser;
    }

    public User updateUser(Long id, User user) throws  UserNotFoundException
    {
        Optional<User> optionalUser =  userRepository.findById(id);;

        if(!optionalUser.isPresent())
            throw  new UserNotFoundException("User Not Found in Data Base, please provide a correct id for user");

        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(Long id)
    {
        Optional<User> optionalUser =  userRepository.findById(id);;

        if(!optionalUser.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found in Data Base, please provide a correct id for user");

        userRepository.deleteById(id);

    }
}
