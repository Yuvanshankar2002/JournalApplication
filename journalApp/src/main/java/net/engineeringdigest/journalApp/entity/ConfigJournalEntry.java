package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection ="Config")
@NoArgsConstructor
public class ConfigJournalEntry {

    private String key;
    private String value;
}
