package com.learn.journal.controller;

import com.learn.journal.dto.JournalEntryDTO;
import com.learn.journal.entity.JournalEntryEntity;
import com.learn.journal.entity.UserEntity;
import com.learn.journal.service.JournalEntryService;
import com.learn.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntryEntity>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity userEntity = userService.findByUserName(username);
        List<JournalEntryEntity> all = userEntity.getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntryEntity> createEntry(@RequestBody JournalEntryDTO myEntryDTO) {
        try {
            JournalEntryEntity myEntry = JournalEntryEntity.builder()
                    .title(myEntryDTO.getTitle())
                    .content(myEntryDTO.getContent())
                    .sentiment(myEntryDTO.getSentiment())
                    .build();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            journalEntryService.saveNewEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntryEntity> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity userEntity = userService.findByUserName(username);
        List<JournalEntryEntity> list = userEntity.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
        if(!list.isEmpty()) {
            return new ResponseEntity<>(list.get(0), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean deleted = journalEntryService.deleteById(myId, username);
        if(deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntryEntity> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntryDTO journalEntryDTO) {
        JournalEntryEntity newEntry = JournalEntryEntity.builder()
                .title(journalEntryDTO.getTitle())
                .content(journalEntryDTO.getContent())
                .sentiment(journalEntryDTO.getSentiment())
                .build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity userEntity = userService.findByUserName(username);
        List<JournalEntryEntity> list = userEntity.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
        if(!list.isEmpty()) {
            Optional<JournalEntryEntity> optionalJournalEntry = journalEntryService.findById(myId);
            if(optionalJournalEntry.isPresent()) {
                JournalEntryEntity old = optionalJournalEntry.get();
                old.setTitle(!newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
