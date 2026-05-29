package com.learn.journal.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "config_journal_app")
@Data
@Builder
public class ConfigJournalAppEntity {
    @Id
    private ObjectId id;

    @NonNull
    private String key;

    @NonNull
    private String value;
}
