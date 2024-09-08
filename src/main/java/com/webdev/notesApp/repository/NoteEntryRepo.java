package com.webdev.notesApp.repository;

import com.webdev.notesApp.model.entity.NoteEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NoteEntryRepo extends MongoRepository<NoteEntry, ObjectId> {
    Optional<NoteEntry> findById(ObjectId id);
}
