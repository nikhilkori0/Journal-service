package com.learn.journal.service;

import com.learn.journal.entity.JournalEntryEntity;
import com.learn.journal.entity.UserEntity;
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

    public void saveNewEntry(JournalEntryEntity journalEntryEntity, String username) {
        try {
            journalEntryEntity.setDate(LocalDateTime.now());
            JournalEntryEntity saved = journalEntryRepository.save(journalEntryEntity);

            UserEntity userEntity = userService.findByUserName(username);
            userEntity.getJournalEntries().add(saved);
            userService.saveUser(userEntity);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error occurred while saving an entry.", e);
        }
    }

    public void saveEntry(JournalEntryEntity journalEntryEntity) {
        journalEntryEntity.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntryEntity);
    }

    public List<JournalEntryEntity> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntryEntity> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public boolean deleteById(ObjectId id, String username) {
        UserEntity userEntity = userService.findByUserName(username);
        boolean removed = userEntity.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if(removed) {
            userService.saveUser(userEntity);
            journalEntryRepository.deleteById(id);
        }
        return removed;
    }

}
