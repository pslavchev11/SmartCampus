package com.rungroup.web.service;

import com.rungroup.web.dto.GradeDto;

import java.util.List;

public interface GradeService {
    GradeDto saveGrade(GradeDto gradeDto);
    List<GradeDto> findGradesByStudentId(Long studentId);
    List<GradeDto> findGradesByCourseId(Long courseId);
    GradeDto findById(Long id);
}
