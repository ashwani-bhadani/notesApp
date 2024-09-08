package com.webdev.notesApp.controller;

import com.webdev.notesApp.model.entity.User;
import com.webdev.notesApp.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.fetchAllUsers();
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            User createdUser  = userService.saveUser(user);
            log.debug("createdUser:: "+ createdUser);
            return ResponseEntity.status(201).body("User created");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("User creation failed");
        }

    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Optional<User> userFromDb = userService.findUserByUsername(user.getUsername());
        if (userFromDb.isPresent()){
            userFromDb.get().setUsername(user.getUsername());
            userFromDb.get().setPassword(user.getPassword());
            userService.saveUser(userFromDb.get());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
