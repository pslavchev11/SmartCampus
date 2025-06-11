package com.rungroup.web.mapper;

import com.rungroup.web.dto.AnnouncementDto;
import com.rungroup.web.models.Announcement;
import com.rungroup.web.models.Course;
import com.rungroup.web.models.UserEntity;

public class AnnouncementMapper {
    public static Announcement mapToAnnouncement(AnnouncementDto announcement, Course course, UserEntity creator) {
        Announcement announcementDto = Announcement.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .startTime(announcement.getStartTime())
                .endTime(announcement.getEndTime())
                .content(announcement.getContent())
                .photoUrl(announcement.getPhotoUrl())
                .course(course)
                .createdBy(creator)
                .createdOn(announcement.getCreatedOn())
                .updatedOn(announcement.getUpdatedOn())
                .build();
        return announcementDto;
    }

    public static AnnouncementDto mapToAnnouncementDto(Announcement announcement) {
        AnnouncementDto announcementDto = AnnouncementDto.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .startTime(announcement.getStartTime())
                .endTime(announcement.getEndTime())
                .content(announcement.getContent())
                .photoUrl(announcement.getPhotoUrl())
                .createdOn(announcement.getCreatedOn())
                .updatedOn(announcement.getUpdatedOn())
                .courseId(announcement.getCourse() != null
                        ? announcement.getCourse().getId()
                        : null)
                .courseName(announcement.getCourse() != null ? announcement.getCourse().getName() : "")
                .createdBy(announcement.getCreatedBy())
                .build();

        return announcementDto;
    }
}
