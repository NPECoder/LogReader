package com.assignment.logReader.repository;

import java.util.List;

import com.assignment.logReader.repository.dbModel.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    @Autowired
    private EventDao eventDao;

    public Event findByEventId(String eventId) {
        return eventDao.findByEventId(eventId);
    }

    public List<Event> findAllEvents() {
        return eventDao.findAllEvents();
    }

    public void saveEvent(Event event) {
        eventDao.saveEvent(event);
    }

}