package com.learn.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

//	@Bean
//	public PlatformTransactionManager addPlatformTransactionManager(MongoDatabaseFactory databaseFactory) {
//		return new MongoTransactionManager(databaseFactory);
//	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
