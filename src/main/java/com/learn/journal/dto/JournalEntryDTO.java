package com.learn.journal.dto;

import com.learn.journal.enums.Sentiment;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class JournalEntryDTO {
    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;

    private Sentiment sentiment;
}
