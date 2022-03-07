package com.assignment.logReader.repository;

import com.assignment.logReader.repository.dbModel.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventDao implements IEventCRUD {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_NEW_EVENT = "INSERT INTO EVENT(ID, Duration, Type, Host, AlertFlag) VALUES(?,?,?,?,?)";
    private static final String SQL_FIND_ALL_EVENT = "SELECT * FROM EVENT";
    private static final String SQL_FIND_BY_EVENT_ID = "SELECT * FROM EVENT WHERE ID = ?";

    public Event findByEventId(String eventId) {
        Event event = jdbcTemplate.queryForObject(SQL_FIND_BY_EVENT_ID, new EventRowMapper(),
                new Object[]{eventId});
        return event;
    }

    public List<Event> findAllEvents() {
        List<Event> events = jdbcTemplate.query(SQL_FIND_ALL_EVENT, new EventRowMapper());
        return events;
    }

    public void saveEvent(Event event) {
        jdbcTemplate.update(SQL_NEW_EVENT, new Object[]{event.getId(), event.getDuration()
                , event.getType(), event.getHost(), event.getAlertFlag()});
    }

    public void deleteEvent(String customerId) {
        // TO DO
    }
}