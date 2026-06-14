package com.example.taskapi.dto;

import com.example.taskapi.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequest {

    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;

    private TaskStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Due date is mandatory")
    @Future(message = "Due date must be in the future")
    private LocalDate dueDate; // YYYY-MM-DD
}
