package com.rungroup.web.repository;

import com.rungroup.web.models.Club;
import com.rungroup.web.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e from Event e WHERE e.name LIKE CONCAT('%', :query, '%')")
    List<Event> searchEvents(String query);
}
