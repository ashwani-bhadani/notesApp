package com.webdev.notesApp.service.impl;

import com.webdev.notesApp.model.entity.NoteEntry;
import com.webdev.notesApp.model.entity.NoteRecycleBin;
import com.webdev.notesApp.model.entity.User;
import com.webdev.notesApp.repository.NoteEntryRepo;
import com.webdev.notesApp.repository.NoteRecycleBinRepo;
import com.webdev.notesApp.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NoteEntryServiceImpl {
    @Autowired
    private NoteEntryRepo noteEntryRepo;

    @Autowired
    private NoteRecycleBinRepo noteRecycleBinRepo;

    @Autowired
    private UserServiceImpl userService;

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

    @Transactional
    public void deleteNoteById(ObjectId id, String username) {
        boolean isNotePresent;
        Optional<User> user = userRepo.findUserByUsername(username);
        if (user.isPresent()) {
            isNotePresent = user.get().getNotes().stream().anyMatch( x-> x.getId().equals(id));
            try {
                if (!isNotePresent) {
                    throw new RuntimeException("note entry not found for user!");
                }
                NoteEntry note = findNoteById(id).orElseThrow();
                NoteRecycleBin deletedNote = new NoteRecycleBin();
                deletedNote.setId(note.getId());
                deletedNote.setTitle(note.getTitle());
                deletedNote.setContent(note.getContent());
                deletedNote.setEntryTimeStamp(note.getEntryTimeStamp());
                deletedNote.setLastUpdateTimeStamp(note.getLastUpdateTimeStamp());
                deletedNote.setDeletionTimeStamp(LocalDateTime.now());
                deletedNote.setExpiryTimeStamp(deletedNote.getDeletionTimeStamp().plusDays(30L));
                noteRecycleBinRepo.save(deletedNote);
                noteEntryRepo.deleteById(id);
                user.get().getNotes().removeIf(x -> x.getId().equals(id));
                userService.saveUser(user.get());
            } catch (Exception e) {
                log.info("Note not found with error!");
                throw new RuntimeException("Rollback transaction :: {}", e);
            }
        } else {
            log.info("user not found");
        }

    }

}
