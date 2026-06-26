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
    public List<Task> findAll(String page, String pageSize) {
        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);
        int start = pageInt * pageSizeInt;
        int end = Math.min(start+pageSizeInt, taskMapStore.size());
//        List<Task> tasks = taskMapStore.values().stream()
//                .filter(task -> Boolean.FALSE.equals(task.getIsDelete()))
//                .collect(Collectors.toList());
        List<Task> tasks = new ArrayList<>(taskMapStore.values());
        return tasks.subList(start, end);
    }

    @Override
    public Task update(Task task) {
        taskMapStore.put(task.getUniqueId(), task);
        return task;
    }

    @Override
    public void deleteById(String id) {
        Task oldTask = taskMapStore.get(id);
        oldTask.setIsDelete(Boolean.TRUE);
        taskMapStore.replace(id, oldTask);
        System.out.println("hi");
    }

    @Override
    public boolean existsById(String id) {
        return taskMapStore.containsKey(id);
    }
}
