package com.learn.journal.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Disabled
    @Test
    void testSendEmail() {
        emailService.sendMail("ex@gmail.com", "Testing Java mail sender", "Hi, How are you?");
    }
}
