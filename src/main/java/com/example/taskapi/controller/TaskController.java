package com.example.taskapi.controller;

import com.example.taskapi.dto.ApiResponse;
import com.example.taskapi.dto.CreateTaskRequest;
import com.example.taskapi.dto.TaskResponse;
import com.example.taskapi.dto.UpdateTaskRequest;
import com.example.taskapi.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskService taskService;

    // POST create task
    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @Valid @RequestBody CreateTaskRequest request) throws JsonProcessingException {
        logger.info("Received request to create task: {}", objectMapper.writeValueAsString(request));
        TaskResponse task = taskService.createTask(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.successResponse("Task created successfully", task));
    }

    // GET task by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable String id) {
        logger.info("Received request to get task by id: {}", id);
        TaskResponse task = taskService.getTaskById(id);
        return ResponseEntity.ok(ApiResponse.successResponse("Task fetched successfully", task));
    }

    // GET all tasks
    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks(
            @RequestParam (value = "status", required = false) String status,
            @RequestParam (value = "page") String page,
            @RequestParam (value = "size") String size) {
        logger.info("Received request to get all tasks by page: {} and size: {}", page, size);
        List<TaskResponse> tasks = taskService.getAllTasks(status, page, size);
        return ResponseEntity.ok(ApiResponse.successResponse("Tasks fetched successfully", tasks));
    }

    // PUT update task by id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable String id,
            @Valid @RequestBody UpdateTaskRequest request) {
        logger.info("Received request to update task by id: {}", id);

        TaskResponse updated = taskService.updateTask(id, request);
        return ResponseEntity.ok(ApiResponse.successResponse("Task updated successfully", updated));
    }

    // DELETE delete task by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        logger.info("Received request to delete task by id: {}", id);
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
