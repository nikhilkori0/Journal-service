package com.learn.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

//	@Bean
//	public PlatformTransactionManager addPlatformTransactionManager(MongoDatabaseFactory databaseFactory) {
//		return new MongoTransactionManager(databaseFactory);
//	}
}
