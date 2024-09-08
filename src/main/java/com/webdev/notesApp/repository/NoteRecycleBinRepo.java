package com.webdev.notesApp.repository;

import com.webdev.notesApp.model.entity.NoteRecycleBin;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NoteRecycleBinRepo extends MongoRepository<NoteRecycleBin, ObjectId> {
    Optional<NoteRecycleBin> findById(ObjectId id);
}
