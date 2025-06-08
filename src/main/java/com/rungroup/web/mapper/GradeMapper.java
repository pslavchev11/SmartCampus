package com.rungroup.web.mapper;

import com.rungroup.web.dto.EnrollmentDto;
import com.rungroup.web.dto.GradeDto;
import com.rungroup.web.models.Course;
import com.rungroup.web.models.Grade;
import com.rungroup.web.models.UserEntity;

import java.time.LocalDate;

public class GradeMapper {
    public static GradeDto mapToGradeDto(Grade g) {
        GradeDto dto = new GradeDto();
        dto.setId(g.getId());
        dto.setCourseId(g.getCourse().getId());
        dto.setCourseName(g.getCourse().getName());
        dto.setStudentId(g.getStudent().getId());
        dto.setStudentUsername(g.getStudent().getUsername());
        dto.setGrade(g.getGrade());
        dto.setTypeOfGrade(g.getTypeOfGrade());
        dto.setDateAwarded(g.getDateAwarded());
        dto.setEndorsement(g.getEndorsement());
        dto.setReasonForNoEndorsement(g.getReasonForNoEndorsement());
        return dto;
    }
    public static Grade mapToGrade(GradeDto dto, Course course, UserEntity student) {
        return Grade.builder()
                .id(dto.getId())
                .course(course)
                .student(student)
                .grade(dto.getGrade())
                .typeOfGrade(dto.getTypeOfGrade())
                .endorsement(dto.getEndorsement())
                .reasonForNoEndorsement(dto.getReasonForNoEndorsement())
                .dateAwarded(dto.getDateAwarded() != null
                        ? dto.getDateAwarded()
                        : LocalDate.now())
                .build();
    }
}
