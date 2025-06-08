package com.rungroup.web.service.impl;


import com.rungroup.web.dto.EnrollmentDto;
import com.rungroup.web.mapper.EnrollmentMapper;
import com.rungroup.web.models.Course;
import com.rungroup.web.models.Enrollment;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.CourseRepository;
import com.rungroup.web.repository.EnrollmentRepository;
import com.rungroup.web.repository.GradeRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.service.EnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;
    private final GradeRepository gradeRepo;


    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepo,
                                 UserRepository userRepo,
                                 CourseRepository courseRepo,
                                 GradeRepository gradeRepo
                                 ) {
        this.enrollmentRepo = enrollmentRepo;
        this.userRepo       = userRepo;
        this.courseRepo     = courseRepo;
        this.gradeRepo      = gradeRepo;
    }

    @Override
    public List<EnrollmentDto> findByCourseId(Long courseId) {
        return enrollmentRepo.findByCourseId(courseId)
                .stream()
                .map(enr -> {
                    EnrollmentDto dto = EnrollmentMapper.mapToDto(enr);
                    gradeRepo.findByCourseIdAndStudentId(courseId, enr.getStudent().getId())
                            .ifPresent(grade -> {
                                dto.setGradeId(grade.getId());
                                dto.setGrade(grade.getGrade());
                                dto.setGradeDateAwarded(grade.getDateAwarded());
                                dto.setTypeOfGrade(grade.getTypeOfGrade());
                                dto.setEndorsement(grade.getEndorsement());
                                dto.setReasonForNoEndorsement(grade.getReasonForNoEndorsement());
                            });
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDto> findByStudentId(Long studentId) {
        return enrollmentRepo.findByStudentId(studentId)
                .stream()
                .map(EnrollmentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEnrolled(Long studentId, Long courseId) {
        return enrollmentRepo.existsByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public void enroll(Long studentId, Long courseId) {
        UserEntity student = userRepo.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("No such user: " + studentId));
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("No such course: " + courseId));
        Enrollment e = Enrollment.builder()
                .student(student)
                .course(course)
                .dateAwarded(LocalDate.now())
                .build();
        enrollmentRepo.save(e);
    }
}
