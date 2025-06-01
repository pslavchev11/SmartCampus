package com.rungroup.web.service;

import com.rungroup.web.dto.EventDto;

import java.util.List;

public interface EventService {

    void createEvent(Long clubId, EventDto eventDto);

    List<EventDto> findAllEvents();

    EventDto findByEventId(Long eventId);

    void updateEvent(EventDto eventDto);

    void deleteEvent(long eventId);

    List<EventDto> searchEvents(String query);
}
