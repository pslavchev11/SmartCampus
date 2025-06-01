package com.rungroup.web.controller;

import com.rungroup.web.dto.CourseDto;
import com.rungroup.web.dto.EnrollmentDto;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.service.CourseService;
import com.rungroup.web.service.EnrollmentService;
import com.rungroup.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@Controller
public class EnrollmentController {

    private EnrollmentService enrollmentService;
    private CourseService courseService;
    private UserService userService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService,
                                 CourseService courseService,
                                 UserService userService) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/enrollments")
    public String myEnrollments(Model model, Principal principal) {
        UserEntity student = userService.findByUsername(principal.getName());
        model.addAttribute("enrollments", enrollmentService.findByStudentId(student.getId()));

        return "enrollments-list";
    }

    @PostMapping("/courses/{courseId}/enroll")
    public String enrollInCourse(@PathVariable Long courseId, Principal principal) {
        UserEntity student = userService.findByUsername(principal.getName());
        enrollmentService.enroll(student.getId(), courseId);

        return "redirect:/enrollments";
    }


    @GetMapping("/courses/{courseId}/students")
    public String listEnrolledStudents(@PathVariable Long courseId, Model model, Principal principal) throws AccessDeniedException {
        UserEntity teacher = userService.findByUsername(principal.getName());
        CourseDto course = courseService.findCourseById(courseId);

        if (!course.getCreatedById().equals(teacher.getId())) {
            throw new AccessDeniedException("You are not allowed to view this page");
        }

        List<EnrollmentDto> roster = enrollmentService.findByCourseId(courseId);

        model.addAttribute("course", course);
        model.addAttribute("roster", roster);

        return "enrollments-students-list";
    }
}
