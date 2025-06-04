package com.rungroup.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EnrollmentDto {
    private Long id;
    private LocalDate dateAwarded;
    private Long courseId;
    private String courseName;
    private Long studentId;
    private String studentUsername;
    private Integer uniqueNumber;

    private Double grade;
    private LocalDate gradeDateAwarded;
    private Long gradeId;

    private String typeOfGrade;
    private String endorsement;
}
