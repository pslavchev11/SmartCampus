package com.rungroup.web.service;

import com.rungroup.web.dto.EnrollmentDto;

import java.util.List;

public interface EnrollmentService {

    List<EnrollmentDto> findByCourseId(Long courseId);

    List<EnrollmentDto> findByStudentId(Long studentId);

    boolean isEnrolled(Long studentId, Long courseId);

    void enroll(Long studentId, Long courseId);
}
