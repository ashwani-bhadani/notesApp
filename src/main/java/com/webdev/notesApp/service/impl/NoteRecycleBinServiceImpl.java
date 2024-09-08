package com.webdev.notesApp.service.impl;

import com.webdev.notesApp.model.entity.NoteRecycleBin;
import com.webdev.notesApp.repository.NoteRecycleBinRepo;
import com.webdev.notesApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteRecycleBinServiceImpl {
    @Autowired
    private NoteRecycleBinRepo noteRecycleBinRepo;

    @Autowired
    private UserRepo userRepo;

    public NoteRecycleBin saveNoteEntered(NoteRecycleBin note) {
        NoteRecycleBin noteSaved = noteRecycleBinRepo.save(note);
        return noteSaved;
    }

    public Optional<NoteRecycleBin> findNoteById(ObjectId id) {
        return noteRecycleBinRepo.findById(id);
    }
    public List<NoteRecycleBin> fetchAllNotes() {
        return noteRecycleBinRepo.findAll();
    }

    public void deleteNoteById(ObjectId id) {
            noteRecycleBinRepo.deleteById(id);
    }

}
