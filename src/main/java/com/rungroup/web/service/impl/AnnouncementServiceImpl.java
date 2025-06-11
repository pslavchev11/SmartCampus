package com.rungroup.web.service.impl;

import com.rungroup.web.dto.AnnouncementDto;
import com.rungroup.web.dto.EnrollmentDto;
import com.rungroup.web.mapper.AnnouncementMapper;
import com.rungroup.web.models.Announcement;
import com.rungroup.web.models.Course;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.AnnouncementRepository;
import com.rungroup.web.repository.CourseRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.AnnouncementService;
import com.rungroup.web.service.CourseService;
import com.rungroup.web.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rungroup.web.mapper.AnnouncementMapper.mapToAnnouncement;
import static com.rungroup.web.mapper.AnnouncementMapper.mapToAnnouncementDto;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private AnnouncementRepository announcementRepository;
    private UserRepository userRepository;
    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private CourseRepository courseRepository;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, UserRepository userRepository, CourseService courseService, EnrollmentService enrollmentService, CourseRepository courseRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<AnnouncementDto> findAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAll();
        return announcements.stream().map((announcement) ->
                mapToAnnouncementDto(announcement))
                .collect(Collectors.toList());
    }

    @Override
    public Announcement saveAnnouncement(AnnouncementDto dto) {

        UserEntity creator = userRepository.findByUsername(SecurityUtil.getSessionUser());
        Course course      = courseService.getCourseEntityById(dto.getCourseId());
        Announcement ann   = AnnouncementMapper.mapToAnnouncement(dto, course, creator);

        return announcementRepository.save(ann);
    }

    @Override
    public AnnouncementDto findAnnouncementById(long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId).get();
        return mapToAnnouncementDto(announcement);
    }

    @Override
    public void updateAnnouncement(AnnouncementDto dto) {
        UserEntity creator = userRepository.findByUsername(SecurityUtil.getSessionUser());
        Course course      = courseService.getCourseEntityById(dto.getCourseId());
        Announcement ann   = AnnouncementMapper.mapToAnnouncement(dto, course, creator);

        announcementRepository.save(ann);
    }

    @Override
    public void delete(long announcementId) {
        announcementRepository.deleteById(announcementId);
    }

    public List<AnnouncementDto> findAnnouncementsForUser(String username) {
        UserEntity me = userRepository.findByUsername(username);

        if (me.getRoles().stream().anyMatch(r -> "TEACHER".equals(r.getName()))) {
            return announcementRepository
                    .findByCreatedBy(me)
                    .stream()
                    .map(AnnouncementMapper::mapToAnnouncementDto)
                    .collect(Collectors.toList());
        } else {
            List<Long> courseIds = enrollmentService
                    .findByStudentId(me.getId())
                    .stream()
                    .map(EnrollmentDto::getCourseId)
                    .toList();
            return announcementRepository
                    .findByCourseIdIn(courseIds)
                    .stream()
                    .map(AnnouncementMapper::mapToAnnouncementDto)
                    .collect(Collectors.toList());
        }
    }


}
