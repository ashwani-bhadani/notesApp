package com.webdev.notesApp.repository;

import com.webdev.notesApp.model.entity.NoteEntry;
import com.webdev.notesApp.model.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, ObjectId> {
    Optional<User> findUserByUsername(String username);
}
