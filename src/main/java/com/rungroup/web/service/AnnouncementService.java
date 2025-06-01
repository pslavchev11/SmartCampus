package com.rungroup.web.service;

import com.rungroup.web.dto.AnnouncementDto;
import com.rungroup.web.models.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<AnnouncementDto> findAllAnnouncements();

    Announcement saveAnnouncement(AnnouncementDto announcement);

    AnnouncementDto findAnnouncementById(long announcementId);

    void updateAnnouncement(AnnouncementDto announcement);

    void delete(long announcementId);
}
