package com.assignment.logReader.repository.dbModel;

import org.springframework.stereotype.Component;

@Component
public class Event {

    private String id;
    private long duration;
    private String type;
    private String host;
    private Boolean alertFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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

    public Boolean getAlertFlag() {
        return alertFlag;
    }

    public void setAlertFlag(Boolean alertFlag) {
        this.alertFlag = alertFlag;
    }

    public Event(String id, long duration, String type, String host, Boolean alertFlag) {
        this.id = id;
        this.duration = duration;
        this.type = type;
        this.host = host;
        this.alertFlag = alertFlag;
    }

    public Event() {

    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", duration='" + duration + '\'' +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", alertFlag='" + alertFlag + '\'' +
                '}';
    }
}
