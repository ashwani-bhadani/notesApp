package com.webdev.notesApp.controller;

import com.webdev.notesApp.model.entity.NoteEntry;
import com.webdev.notesApp.model.entity.User;
import com.webdev.notesApp.service.impl.NoteEntryServiceImpl;
import com.webdev.notesApp.service.impl.UserServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notepad")
public class NoteEntryrController {

    @Autowired
    private NoteEntryServiceImpl entryService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllNotesForUser(@PathVariable String username) {
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isPresent()) {
           List<NoteEntry> fetchedNotes = entryService.fetchAllNotes();
           if( fetchedNotes!=null && !fetchedNotes.isEmpty()) {
               return new ResponseEntity<>(fetchedNotes, HttpStatus.OK);
           }
        } else {
               return ResponseEntity.status(404).body("User with username not found");
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/{username}")
    public ResponseEntity<NoteEntry> createNote(@RequestBody NoteEntry noteEntry, @PathVariable String username) {
        try {
            noteEntry.setEntryTimeStamp(LocalDateTime.now());
            entryService.saveNoteEntered(noteEntry, username);
            return new ResponseEntity<>(noteEntry, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{noteId}")
    public ResponseEntity<NoteEntry> getNoteById(@PathVariable ObjectId noteId) {
        Optional<NoteEntry> foundNote =  entryService.findNoteById(noteId);
        return foundNote.map(note ->
            new ResponseEntity<>(note , HttpStatus.OK)
        ).orElseGet(
                ()-> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @DeleteMapping("remove/{noteId}")
    public ResponseEntity<?> deleteNoteById(@PathVariable ObjectId noteId) {
        entryService.deleteNoteById(noteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("noteId/{noteId}/username/{username}")
    public ResponseEntity<?> updateNoteById(@RequestBody NoteEntry note, @PathVariable ObjectId noteId, @PathVariable String username) {
        note.setLastUpdateTimeStamp(LocalDateTime.now());
        note.setId(noteId);
        boolean isNoteForUserPresent = false;        Optional<NoteEntry> noteFound = Optional.of(new NoteEntry());

        Optional<User> user = userService.findUserByUsername(username);
        if (user.isPresent()) {
            isNoteForUserPresent = user.get().getNotes().stream()
                                        .anyMatch(notes -> notes.getId().equals(note.getId()));
        }

        if(!isNoteForUserPresent) {
            return ResponseEntity.status(404).body("no such note available with user");
        }

        noteFound = entryService.findNoteById(noteId);
        if(noteFound.isPresent()) {
            if (note.getContent() != null && !note.getContent().isEmpty())
                noteFound.get().setContent(note.getContent());
            if (note.getTitle() != null && !note.getTitle().isEmpty())
                noteFound.get().setTitle(note.getTitle());
            noteFound.get().setLastUpdateTimeStamp(note.getLastUpdateTimeStamp());
            try {
                entryService.saveNoteEntered(noteFound.get(),username);
                return new ResponseEntity<>(noteFound.get(), HttpStatus.ACCEPTED);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
