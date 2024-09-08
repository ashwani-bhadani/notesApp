package com.webdev.notesApp.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * stores deleted notes to recycle bin & a chron job will remove 30 day stale notes
 */
@Data
@NoArgsConstructor
@Document(collection = "noteRecycleBin", collation = "en")
public class NoteRecycleBin {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime entryTimeStamp;
    private LocalDateTime lastUpdateTimeStamp;
    private LocalDateTime deletionTimeStamp; //day when record was deleted
    private LocalDateTime lastAccessTmeStamp; //day when note in recycle bin was accessed
    private LocalDateTime expiryTimeStamp; //day +30 days from lastAccessTmeStamp to expire note

}
