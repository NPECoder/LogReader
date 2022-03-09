package com.assignment.logReader.repository;

import com.assignment.logReader.repository.dbModel.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EventDao implements IEventCRUD {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcTemplate getJdbcTemplate() {

        return new JdbcTemplate(dataSource);
    }

    private static final String SQL_NEW_EVENT = "INSERT INTO EVENT(ID, Duration, Type, Host, AlertFlag) VALUES(?,?,?,?,?)";
    private static final String SQL_FIND_ALL_EVENT = "SELECT * FROM EVENT";
    private static final String SQL_FIND_BY_EVENT_ID = "SELECT * FROM EVENT WHERE ID = ?";

    public Event findByEventId(String eventId) {
        Event event = jdbcTemplate.queryForObject(SQL_FIND_BY_EVENT_ID, new EventRowMapper(),
                new Object[]{eventId});
        return event;
    }

    public List<Event> findAllEvents() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        List<Event> events = jdbcTemplate.query(SQL_FIND_ALL_EVENT, new EventRowMapper());
        return events;
    }

    public void saveEvent(Event event) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        jdbcTemplate.update(SQL_NEW_EVENT, new Object[]{event.getId(), event.getDuration()
                , event.getType(), event.getHost(), event.getAlertFlag()});


        /*
        //print All Event insert in DB  - For testing - removed post test
        List<Event> events = jdbcTemplate.query(SQL_FIND_ALL_EVENT, new EventRowMapper());
        events.stream().forEach(e -> System.out.println(e.toString()));*/
    }

    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("classpath:Event_CreateScript.sql")
                .build();
        return db;
    }

    public void deleteEvent(String customerId) {
        // TO DO
    }
}