package com.learn.journal.service;

import com.learn.journal.entity.User;
import com.learn.journal.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserName() {
        User user = userRepository.findByUsername("ram");
        Assertions.assertNotNull(user);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,3,5",
            "4,2,9"
    })
    public void test(int a, int b, int expected) {
        Assertions.assertEquals(expected,a+b);
    }
}
