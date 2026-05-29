package com.learn.journal.dto;

import com.learn.journal.entity.JournalEntryEntity;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    @NonNull
    private String username;

    @NonNull
    private String password;

    private List<JournalEntryEntity> journalEntries = new ArrayList<>();
}
