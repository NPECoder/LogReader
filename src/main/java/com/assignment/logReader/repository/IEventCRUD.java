package com.assignment.logReader.repository;

import com.assignment.logReader.repository.dbModel.Event;

import java.util.List;

public interface IEventCRUD {

    public Event findByEventId(String eventId);

    public List<Event> findAllEvents();

    public void saveEvent(Event event);

    public void deleteEvent(String eventId);
}
