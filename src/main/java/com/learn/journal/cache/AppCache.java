package com.learn.journal.cache;

import com.learn.journal.entity.ConfigJournalAppEntity;
import com.learn.journal.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
public class AppCache {

    @Autowired
    ConfigJournalAppRepository configJournalAppRepository;

    public enum keys {
        WEATHER_API
    }

    private Map<String, String> cache;

    @PostConstruct
    public void init() {
        cache = new HashMap<>();

        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all) {
            cache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
