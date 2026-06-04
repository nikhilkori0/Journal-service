package com.learn.journal.scheduler;

import com.learn.journal.cache.AppCache;
import com.learn.journal.entity.JournalEntryEntity;
import com.learn.journal.entity.UserEntity;
import com.learn.journal.enums.Sentiment;
import com.learn.journal.model.SentimentData;
import com.learn.journal.repository.UserRepositoryImpl;
import com.learn.journal.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;


    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 0 9 ? * SUN")
    public void sendSentimentAnalysisMail() {
        List<UserEntity> users = userRepository.getUserForSentimentAnalysis();
        for (UserEntity user : users) {
            List<JournalEntryEntity> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                                            .filter(x -> x.getSentiment() != null && x.getDate().isAfter(LocalDateTime.now().minusDays(7)))
                                            .map(JournalEntryEntity::getSentiment)
                                            .toList();
            if(!sentiments.isEmpty()) {
                Map<Sentiment, Integer> sentimentFreq = new HashMap<>();
                Sentiment mostFrequentSentiment = null;
                int maxCount = 0;
                int newValue;
                for (Sentiment sentiment : sentiments) {
                    newValue = sentimentFreq.getOrDefault(sentiment, 0) + 1;
                    sentimentFreq.put(sentiment, newValue);
                    if(newValue > maxCount) {
                        maxCount = newValue;
                        mostFrequentSentiment = sentiment;
                    }
                }

                if (mostFrequentSentiment != null) {
                    SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days - " + mostFrequentSentiment).build();
                    kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                }
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        log.info("::Scheduler - Clear App Cache::");
        appCache.init();
    }
}
