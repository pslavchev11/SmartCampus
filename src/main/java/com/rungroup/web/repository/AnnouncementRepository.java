package com.rungroup.web.repository;

import com.rungroup.web.models.Announcement;
import com.rungroup.web.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByCreatedBy(UserEntity creator);


    List<Announcement> findByCourseIdIn(List<Long> courseIds);
}
