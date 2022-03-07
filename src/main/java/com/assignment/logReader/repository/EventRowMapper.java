package com.assignment.logReader.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.assignment.logReader.repository.dbModel.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


public class EventRowMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event();

        event.setId(rs.getString("Id"));
        event.setDuration(rs.getInt("Duration"));
        event.setType(rs.getString("Type"));
        event.setHost(rs.getString("Host"));
        event.setAlertFlag(rs.getBoolean("AlertFlag"));

        return event;
    }

}