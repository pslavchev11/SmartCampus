package com.rungroup.web.service.impl;

import com.rungroup.web.dto.AnnouncementDto;
import com.rungroup.web.mapper.AnnouncementMapper;
import com.rungroup.web.models.Announcement;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.AnnouncementRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.AnnouncementService;
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

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
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

        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Announcement announcement = AnnouncementMapper.mapToAnnouncement(dto);

        announcement.setCreatedBy(user);

        return announcementRepository.save(announcement);
    }

    @Override
    public AnnouncementDto findAnnouncementById(long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId).get();
        return mapToAnnouncementDto(announcement);
    }

    @Override
    public void updateAnnouncement(AnnouncementDto announcementDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Announcement announcement = mapToAnnouncement(announcementDto);
        announcement.setCreatedBy(user);

        announcementRepository.save(announcement);
    }

    @Override
    public void delete(long announcementId) {
        announcementRepository.deleteById(announcementId);
    }

}
