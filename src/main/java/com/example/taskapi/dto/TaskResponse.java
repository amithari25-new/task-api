package com.example.taskapi.dto;

import com.example.taskapi.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private String uniqueId;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
}
