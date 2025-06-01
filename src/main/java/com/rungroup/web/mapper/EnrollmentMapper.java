package com.rungroup.web.mapper;

import com.rungroup.web.dto.EnrollmentDto;
import com.rungroup.web.models.Enrollment;

public class EnrollmentMapper {
    public static EnrollmentDto mapToDto(Enrollment e) {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setId(e.getId());
        dto.setCourseId(e.getCourse().getId());
        dto.setCourseName(e.getCourse().getName());
        dto.setStudentId(e.getStudent().getId());
        dto.setStudentUsername(e.getStudent().getUsername());
        dto.setDateAwarded(e.getDateAwarded());
        return dto;
    }
}
