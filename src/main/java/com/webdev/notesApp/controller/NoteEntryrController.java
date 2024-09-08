package com.webdev.notesApp.controller;

import com.webdev.notesApp.model.entity.NoteEntry;
import com.webdev.notesApp.service.impl.NoteEntryServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<NoteEntry> getAll() {
        return entryService.fetchAllNotes();
    }

    @PostMapping
    public void createEntry(@RequestBody NoteEntry noteEntry) {
        noteEntry.setEntryTimeStamp(LocalDateTime.now());
        entryService.saveNoteEntered(noteEntry);
    }

    @GetMapping("id/{noteId}")
    public NoteEntry getNoteById(@PathVariable ObjectId noteId) {
        return entryService.findNoteById(noteId).orElse(null);
    }

    @DeleteMapping("remove/{noteId}")
    public void deleteNoteById(@PathVariable ObjectId noteId) {
        entryService.deleteNoteById(noteId);
    }

    @PutMapping("id/{noteId}")
    public boolean updateNoteById(@RequestBody NoteEntry note, @PathVariable ObjectId noteId) {
        Optional<NoteEntry> noteFound = entryService.findNoteById(noteId);
        if(noteFound.isPresent()) {
            if (note.getContent() != null && !note.getContent().isEmpty())
                noteFound.get().setContent(note.getContent());
            if (note.getTitle() != null && !note.getTitle().isEmpty())
                noteFound.get().setTitle(note.getTitle());
            entryService.saveNoteEntered(noteFound.get());
            return true;
        }
        return false;
    }

}
