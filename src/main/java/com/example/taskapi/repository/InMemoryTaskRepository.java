package com.example.taskapi.repository;

import com.example.taskapi.model.Task;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTaskRepository implements TaskRepository {


    // Using ConcurrentHashMap for thread-safe operations
    private final Map<String, Task> taskMapStore = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        taskMapStore.put(task.getUniqueId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(taskMapStore.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(taskMapStore.values());
    }

    @Override
    public Task update(Task task) {
        taskMapStore.put(task.getUniqueId(), task);
        return task;
    }

    @Override
    public void deleteById(String id) {
        taskMapStore.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        return taskMapStore.containsKey(id);
    }
}
