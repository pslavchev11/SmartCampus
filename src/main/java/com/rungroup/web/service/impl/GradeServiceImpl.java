package com.rungroup.web.service.impl;

import com.rungroup.web.dto.GradeDto;
import com.rungroup.web.mapper.GradeMapper;
import com.rungroup.web.models.Course;
import com.rungroup.web.models.Grade;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.CourseRepository;
import com.rungroup.web.repository.GradeRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rungroup.web.mapper.GradeMapper.mapToGradeDto;

@Service
public class GradeServiceImpl implements GradeService {

    private GradeRepository gradeRepository;
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository,
                            CourseRepository courseRepository,
                            UserRepository userRepository) {
        this.gradeRepository = gradeRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }


    @Override
    public GradeDto saveGrade(GradeDto gradeDto) {
        Course course = courseRepository.findById(gradeDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        UserEntity student = userRepository.findById(gradeDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Grade grade = GradeMapper.mapToGrade(gradeDto, course, student);

        String teacherUsername = SecurityUtil.getSessionUser();
        UserEntity teacher = userRepository.findByUsername(teacherUsername);
        grade.setCreatedBy(teacher);
        grade = gradeRepository.save(grade);

        return GradeMapper.mapToGradeDto(grade);
    }

    @Override
    public List<GradeDto> findGradesByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(GradeMapper::mapToGradeDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeDto> findGradesByCourseId(Long courseId) {
        return gradeRepository.findByCourseId(courseId)
                .stream()
                .map(GradeMapper::mapToGradeDto)
                .collect(Collectors.toList());
    }

    @Override
    public GradeDto findById(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
        return mapToGradeDto(grade);
    }
}
