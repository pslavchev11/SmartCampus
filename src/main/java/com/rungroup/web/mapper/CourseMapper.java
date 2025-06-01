package com.rungroup.web.mapper;

import com.rungroup.web.dto.CourseDto;
import com.rungroup.web.models.Course;

public class CourseMapper {
    public static CourseDto mapToCourseDto(Course course) {
        CourseDto courseDto = CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .photoUrl(course.getPhotoUrl())
                .startTime(course.getStartTime())
                .endTime(course.getEndTime())
                .color(course.getColor())
                .semesterStart(course.getSemesterStart())
                .semesterEnd(course.getSemesterEnd())
                .dayOfWeek(course.getDayOfWeek())
                .createdById(course.getCreatedBy().getId())
                .createdByUsername(course.getCreatedBy().getUsername())
                .build();

        return courseDto;
    }

    public static Course mapToCourse(CourseDto courseDto) {
        Course course = Course.builder()
                .id(courseDto.getId())
                .name(courseDto.getName())
                .description(courseDto.getDescription())
                .photoUrl(courseDto.getPhotoUrl())
                .startTime(courseDto.getStartTime())
                .endTime(courseDto.getEndTime())
                .semesterStart(courseDto.getSemesterStart())
                .semesterEnd(courseDto.getSemesterEnd())
                .dayOfWeek(courseDto.getDayOfWeek())
                .color(courseDto.getColor())
//                .createdBy(courseDto.getCreatedBy())
                .build();

        return course;
    }
}
