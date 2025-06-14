package com.rungroup.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rungroup.web.dto.CourseDto;
import com.rungroup.web.models.Course;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.CourseService;
import com.rungroup.web.service.EnrollmentService;
import com.rungroup.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class CourseController {
    private CourseService courseService;
    private UserService userService;
    private EnrollmentService enrollmentService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.userService = userService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/courses")
    public String listCourses(Model model) throws Exception {
        String username = SecurityUtil.getSessionUser();
        UserEntity me = null;
        boolean isStudent = false, isTeacher = false;
        if (username != null) {
            me = userService.findByUsername(username);
            model.addAttribute("user", me);
            for (var r : me.getRoles()) {
                if ("STUDENT".equals(r.getName())) isStudent = true;
                if ("TEACHER".equals(r.getName())) isTeacher = true;
            }
        }
        List<CourseDto> allCourses = courseService.findAllCourses();
        model.addAttribute("courses", allCourses);

        List<CourseDto> forCalendar;
        if (isStudent) {
            long studentId = me.getId();
            forCalendar = allCourses.stream()
                    .filter(c -> enrollmentService.isEnrolled(studentId, c.getId()))
                    .toList();
        } else if (isTeacher) {
            long teacherId = me.getId();
            forCalendar = allCourses.stream()
                    .filter(c -> c.getCreatedById().equals(teacherId))
                    .toList();
        } else {
            forCalendar = allCourses;
        }

        List<Map<String,Object>> fcEvents = forCalendar.stream()
                .map(c -> {
                    Map<String,Object> ev = new HashMap<>();
                    ev.put("id",    c.getId());
                    ev.put("title", c.getName());
                    String color = Optional.ofNullable(c.getColor()).orElse("#003366");
                    ev.put("backgroundColor", color);
                    ev.put("borderColor",     color);
                    ev.put("textColor",       color);
                    if (c.getSemesterStart()!=null && c.getSemesterEnd()!=null && c.getDayOfWeek()!=null) {
                        ev.put("daysOfWeek", List.of(c.getDayOfWeek() % 7));
                        ev.put("startRecur", c.getSemesterStart().toString());
                        ev.put("endRecur",   c.getSemesterEnd().toString());
                        if (c.getStartTime()!=null && c.getEndTime()!=null) {
                            ev.put("startTime", c.getStartTime().toString());
                            ev.put("endTime",   c.getEndTime().toString());
                        }
                    } else {
                        if (c.getStartTime()!=null) ev.put("start", c.getStartTime().toString());
                        if (c.getEndTime()!=null)   ev.put("end",   c.getEndTime().toString());
                    }
                    return ev;
                })
                .toList();

        String eventsJson = new ObjectMapper().writeValueAsString(fcEvents);
        model.addAttribute("eventsJson", eventsJson);

        return "courses-list";
    }

    @GetMapping("/courses/{courseId}")
    public String courseDetail(@PathVariable("courseId") long courseId, Model model) {
        UserEntity user = new UserEntity();
        CourseDto courseDto = courseService.findCourseById(courseId);
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);

            boolean alreadyEnrolled = user.getRoles().stream()
                    .anyMatch(r -> "STUDENT".equals(r.getName()))
                    ? enrollmentService.isEnrolled(user.getId(), courseId)
                    : false;
            model.addAttribute("alreadyEnrolled", alreadyEnrolled);
        }

        model.addAttribute("course", courseDto);
        return "courses-detail";
    }

    @GetMapping("/courses/new")
    public String createCourseForm(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        return "courses-create";
    }

    @PostMapping("/courses/new")
    public String saveCourse(@Valid @ModelAttribute("course") CourseDto course, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("course", course);
            return "courses-create";
        }
        courseService.saveCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses/{courseId}/edit")
    public String editCourseForm(@ModelAttribute("courseId") long courseId, Model model) {
        CourseDto course = courseService.findCourseById(courseId);
        model.addAttribute("course", course);
        return "courses-edit";
    }

    @PostMapping("/courses/{courseId}/edit")
    public String updateCourse(@ModelAttribute("courseId") long courseId, BindingResult result,
                                @Valid @ModelAttribute("course") CourseDto course, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("course", course);
            return "courses-edit";
        }
        course.setId(courseId);
        courseService.updateCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses/{courseId}/delete")
    public String deleteCourse(@PathVariable("courseId") long courseId) {
        courseService.delete(courseId);
        return "redirect:/courses";
    }
}
