package com.rungroup.web.dto;

import com.rungroup.web.models.UserEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class CourseDto {

    private Long id;
    @NotEmpty(message = "The course name should not be empty!")
    private String name;
    @NotEmpty(message = "The course description should not be empty!")
    private String description;
    private String photoUrl;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private String color;
    @NotNull(message="Semester start is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate semesterStart;
    @NotNull(message="Semester end is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate semesterEnd;
    @NotNull(message = "Please pick a day of the week!")
    @Min(1) @Max(7)
    private Integer dayOfWeek;
    private Long createdById;
    private String createdByUsername;
}
