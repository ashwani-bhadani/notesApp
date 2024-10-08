package com.webdev.notesApp.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Document(collection = "noteEntry",
        collation = "en"    // collation to set for local-time https://www.mongodb.com/docs/manual/reference/collation-locales-defaults/
)
public class NoteEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime entryTimeStamp;
    private LocalDateTime lastUpdateTimeStamp;

}
