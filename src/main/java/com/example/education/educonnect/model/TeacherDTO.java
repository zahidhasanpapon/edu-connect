package com.example.education.educonnect.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeacherDTO {
    private String name;
    private String email;
    private String phone;
    private String departmentName;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
