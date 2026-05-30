package com.learn.journal.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
public class UserEntity {
    @Id
    private ObjectId id;

    @Indexed(unique = true) //to activate indexing, set property in properties file
    @NonNull
    private String username;

    @NonNull
    private String password;

    @DBRef
    private List<JournalEntryEntity> journalEntries = new ArrayList<>();

    private List<String> roles;

    private String email;

    private boolean sentimentAnalysis;
}
