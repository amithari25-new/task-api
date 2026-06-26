package com.example.taskapi.service;

import com.example.taskapi.dto.CreateTaskRequest;
import com.example.taskapi.dto.TaskResponse;
import com.example.taskapi.dto.UpdateTaskRequest;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request);

    TaskResponse getTaskById(String id);

    List<TaskResponse> getAllTasks(String status, String page, String size);

    TaskResponse updateTask(String id, UpdateTaskRequest request);

    void deleteTask(String id);
}
