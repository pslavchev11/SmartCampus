package com.rungroup.web.service;

import com.rungroup.web.dto.CourseDto;
import com.rungroup.web.models.Course;

import java.util.List;

public interface CourseService {
    List<CourseDto> findAllCourses();

    Course saveCourse(CourseDto course);

    CourseDto findCourseById(long courseId);

    Course getCourseEntityById(long id);

    void updateCourse(CourseDto course);

    void delete(long courseId);
}
