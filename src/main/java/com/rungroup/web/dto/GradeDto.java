package com.rungroup.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {
    private Long id;
    private Long courseId;
    private String courseName;
    private Long studentId;
    private String studentUsername;
    private Double grade;
    private String typeOfGrade;
    private LocalDate dateAwarded;
    private String endorsement;
    private String reasonForNoEndorsement;
}
