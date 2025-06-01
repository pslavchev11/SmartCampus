package com.rungroup.web.controller;

import com.rungroup.web.dto.CourseDto;
import com.rungroup.web.dto.GradeDto;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.service.CourseService;
import com.rungroup.web.service.GradeService;
import com.rungroup.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class GradeController {
    private GradeService gradeService;
    private UserService userService;
    private CourseService courseService;

    @Autowired
    public GradeController(GradeService gradeService, UserService userService, CourseService courseService) {
        this.gradeService = gradeService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/grades")
    public String studentGrades(Model model, Principal principal) {
        UserEntity student = userService.findByUsername(principal.getName());
        model.addAttribute("grades", gradeService.findGradesByStudentId(student.getId()));

        return "grades-list";
    }

    @GetMapping("/grades/new")
    public String showGradeForm(@RequestParam Long courseId,
                                @RequestParam Long studentId,
                                Model model){
        GradeDto gradeDto = new GradeDto();
        gradeDto.setCourseId(courseId);
        gradeDto.setStudentId(studentId);

        CourseDto course = courseService.findCourseById(courseId);
        gradeDto.setCourseName(course.getName());

        UserEntity student = userService.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        gradeDto.setStudentUsername(student.getUsername());

        gradeDto.setDateAwarded(LocalDate.now());

        model.addAttribute("gradeDto", gradeDto);

        return "grades-create";
    }

    @PostMapping("/grades/new")
    public String saveGrade(@ModelAttribute("gradeDto") GradeDto gradeDto){
        gradeService.saveGrade(gradeDto);
        return "redirect:/courses/" + gradeDto.getCourseId() + "/students";
    }

    @GetMapping("/grades/{id}/edit")
    public String showEditGradeForm(@PathVariable Long id, Model model) {
        GradeDto gradeDto = gradeService.findById(id);
        model.addAttribute("gradeDto", gradeDto);
        return "grades-create";
    }
}
