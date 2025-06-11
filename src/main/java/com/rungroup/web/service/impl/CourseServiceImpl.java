package com.rungroup.web.service.impl;

import com.rungroup.web.dto.CourseDto;
import com.rungroup.web.mapper.CourseMapper;
import com.rungroup.web.models.Course;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.CourseRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rungroup.web.mapper.CourseMapper.mapToCourse;
import static com.rungroup.web.mapper.CourseMapper.mapToCourseDto;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;
    private UserRepository userRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CourseDto> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map((course) -> mapToCourseDto(course)).collect(Collectors.toList());
    }

    @Override
    public Course saveCourse(CourseDto dto) {

        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findFirstByUsername(username);
        Course course = CourseMapper.mapToCourse(dto);

        course.setCreatedBy(user);

        return courseRepository.save(course);
    }

    @Override
    public CourseDto findCourseById(long courseId) {
        Course course = courseRepository.findById(courseId).get();
        return mapToCourseDto(course);
    }

    @Override
    public Course getCourseEntityById(long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + id));
    }

    @Override
    public void updateCourse(CourseDto courseDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findFirstByUsername(username);
        Course course = CourseMapper.mapToCourse(courseDto);
        course.setCreatedBy(user);

        courseRepository.save(course);
    }

    @Override
    public void delete(long courseId) {
        courseRepository.deleteById(courseId);
    }
}
