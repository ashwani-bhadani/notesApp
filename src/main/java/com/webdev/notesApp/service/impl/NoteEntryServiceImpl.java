package com.webdev.notesApp.service.impl;

import com.webdev.notesApp.model.entity.NoteEntry;
import com.webdev.notesApp.repository.NoteEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteEntryServiceImpl {
    @Autowired
    private NoteEntryRepo noteEntryRepo;

    public void saveNoteEntered(NoteEntry note) {
        noteEntryRepo.save(note);
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
