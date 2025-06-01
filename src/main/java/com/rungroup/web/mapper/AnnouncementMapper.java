package com.rungroup.web.mapper;

import com.rungroup.web.dto.AnnouncementDto;
import com.rungroup.web.models.Announcement;

public class AnnouncementMapper {
    public static Announcement mapToAnnouncement(AnnouncementDto announcement) {
        Announcement announcementDto = Announcement.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .startTime(announcement.getStartTime())
                .endTime(announcement.getEndTime())
                .content(announcement.getContent())
                .photoUrl(announcement.getPhotoUrl())
                .createdBy(announcement.getCreatedBy())
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
                .createdBy(announcement.getCreatedBy())
                .photoUrl(announcement.getPhotoUrl())
                .createdOn(announcement.getCreatedOn())
                .updatedOn(announcement.getUpdatedOn())
                .build();

        return announcementDto;
    }
}
