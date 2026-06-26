package com.example.taskapi.repository;

import com.example.taskapi.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepositoryMongo extends MongoRepository<Task, String> {
    Task findByUniqueId(String id);
}
