package com.webdev.notesApp.service.impl;



import com.webdev.notesApp.model.entity.User;
import com.webdev.notesApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepo userRepo;

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    public List<User> fetchAllUsers() {
        return userRepo.findAll();
    }

    public void deleteUserById(ObjectId id) {
        userRepo.deleteById(id);
    }

}
