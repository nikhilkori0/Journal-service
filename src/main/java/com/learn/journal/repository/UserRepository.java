package com.learn.journal.repository;

import com.learn.journal.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUsername(String username);

    void deleteByUsername(String username);
}
