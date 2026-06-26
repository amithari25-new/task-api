package com.example.taskapi.service;

import com.example.taskapi.dto.CreateTaskRequest;
import com.example.taskapi.dto.TaskResponse;
import com.example.taskapi.dto.UpdateTaskRequest;
import com.example.taskapi.enums.TaskStatus;
import com.example.taskapi.exception.TaskNotFoundException;
import com.example.taskapi.model.Task;
import com.example.taskapi.repository.TaskRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

//    @Autowired
//    private TaskRepository taskRepository;
    @Autowired
    private TaskRepositoryMongo taskRepository;

    @Override
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = Task.builder()
                .uniqueId(UUID.randomUUID().toString())
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.PENDING)
                .dueDate(request.getDueDate())
                .isDelete(Boolean.FALSE)
                .build();

        Task saved = taskRepository.save(task);
        return convertToResponseDto(saved);
    }

    @Override
    public TaskResponse getTaskById(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        return convertToResponseDto(task);
    }

    @Override
    public List<TaskResponse> getAllTasks(String status, String page, String pageSize) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(pageSize), Sort.by("dueDate").descending());
        Page<Task> taskPage = taskRepository.findAll(pageable);
        List<Task> tasks = taskPage.toList();
//        List<Task> tasks = taskRepository.findAll(page, pageSize);
        if (StringUtils.hasText(status)){
            tasks = tasks.stream()
                    .filter(task -> Objects.equals(task.getStatus().toString(), status))
                    .collect(Collectors.toList());
        }
//        tasks.sort(Comparator.comparing(Task:: getDueDate).reversed()); // descending order
        List<TaskResponse> responses = new ArrayList<>();
        for(Task task: tasks){
            responses.add(convertToResponseDto(task));
        }
        return responses;
    }

    @Override
    public TaskResponse updateTask(String id, UpdateTaskRequest request) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        existing.setTitle(request.getTitle() != null ? request.getTitle() : existing.getTitle());
        existing.setDescription(request.getDescription() != null ? request.getDescription() : existing.getDescription());
        existing.setStatus(request.getStatus() != null ? request.getStatus() : existing.getStatus());
        existing.setDueDate(request.getDueDate() != null ? request.getDueDate() : existing.getDueDate());

//        Task updated = taskRepository.update(existing);
        Task updated = taskRepository.save(existing);
        return convertToResponseDto(updated);
    }

    @Override
    public void deleteTask(String id) {
        Task task = taskRepository.findByUniqueId(id);
        if (Objects.isNull(task)) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        task.setIsDelete(true);
//        taskRepository.deleteById(id);
        taskRepository.save(task);
    }

    private TaskResponse convertToResponseDto(Task task) {
        return TaskResponse.builder()
                .uniqueId(task.getUniqueId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .isDeleted(task.getIsDelete())
                .dueDate(task.getDueDate())
                .build();
    }
}
