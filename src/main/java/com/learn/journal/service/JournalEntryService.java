package com.learn.journal.service;

import com.learn.journal.entity.JournalEntry;
import com.learn.journal.entity.User;
import com.learn.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveNewEntry(JournalEntry journalEntry, String username) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);

            User user = userService.findByUserName(username);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error occurred while saving an entry.", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public boolean deleteById(ObjectId id, String username) {
        User user = userService.findByUserName(username);
        boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if(removed) {
            userService.saveUser(user);
            journalEntryRepository.deleteById(id);
        }
        return removed;
    }

}
