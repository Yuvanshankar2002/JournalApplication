package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document(collection ="Journal_Entries")
public class JournalEntry {
    @Id
    private ObjectId id;
    private String title;
    private String content;

    private LocalDateTime date;

}
