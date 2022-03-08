package com.assignment.logReader.models;

import org.springframework.stereotype.Component;

@Component
public class LogRecord {

    String id;
    State state;
    String type;
    String host;
    Long timestamp;

    @Override
    public String toString() {
        return "LogEntry{" +
                "Id='" + id + '\'' +
                ", state=" + state +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public LogRecord(String id, State state, String type, String host, Long timestamp) {
        id = id;
        this.state = state;
        this.type = type;
        this.host = host;
        this.timestamp = timestamp;
    }

    public LogRecord(String id, State state, Long timestamp) {
        id = id;
        this.state = state;
        this.timestamp = timestamp;
    }

    public LogRecord(){}

        public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
