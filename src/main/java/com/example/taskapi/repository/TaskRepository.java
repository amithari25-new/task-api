package com.example.taskapi.repository;

import com.example.taskapi.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(String id);

    List<Task> findAll();

    Task update(Task task);

    void deleteById(String id);

    boolean existsById(String id);
}
