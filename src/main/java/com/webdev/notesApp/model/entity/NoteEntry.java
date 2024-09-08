package com.webdev.notesApp.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.BsonTimestamp;
import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;


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
