package com.assignment.logReader.repository;

import java.util.List;

import com.assignment.logReader.repository.dbModel.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class EventService {

    @Autowired
    public EventService(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Autowired
    private EventDao eventDao;

    public Event findByEventId(String eventId) {
        return eventDao.findByEventId(eventId);
    }

    public List<Event> findAllEvents() {

        return eventDao.findAllEvents();
    }

    public void saveEvent(Event event) {
        EventDao eventDao = new EventDao();
        eventDao.saveEvent(event);
    }

}