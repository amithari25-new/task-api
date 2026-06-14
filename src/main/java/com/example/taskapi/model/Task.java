package com.example.taskapi.model;

import com.example.taskapi.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private String uniqueId;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate; // YYYY-MM-DD
}
