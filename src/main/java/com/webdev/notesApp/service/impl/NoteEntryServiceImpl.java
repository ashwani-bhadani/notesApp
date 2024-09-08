package com.webdev.notesApp.service.impl;

import com.webdev.notesApp.model.entity.NoteEntry;
import com.webdev.notesApp.model.entity.User;
import com.webdev.notesApp.repository.NoteEntryRepo;
import com.webdev.notesApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteEntryServiceImpl {
    @Autowired
    private NoteEntryRepo noteEntryRepo;

    @Autowired
    private UserRepo userRepo;

    public NoteEntry saveNoteEntered(NoteEntry note, String username) {
        Optional<User> user = userRepo.findUserByUsername(username);
        if (user.isPresent()) {
            NoteEntry noteSaved = noteEntryRepo.save(note);
            user.get().getNotes().add(noteSaved);
            userRepo.save(user.get());
            return noteSaved;
        }
        return null;
    }

    public Optional<NoteEntry> findNoteById(ObjectId id) {
        return noteEntryRepo.findById(id);
    }
    public List<NoteEntry> fetchAllNotes() {
        return noteEntryRepo.findAll();
    }

    public void deleteNoteById(ObjectId id) {
        noteEntryRepo.deleteById(id);
    }

}
