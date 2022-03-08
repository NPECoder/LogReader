package com.assignment.logReader.processor;

import com.assignment.logReader.models.LogRecord;
import com.assignment.logReader.models.State;
import com.assignment.logReader.repository.EventDao;
import com.assignment.logReader.repository.EventService;
import com.assignment.logReader.repository.dbModel.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Component
public class RecordProcessor {

    @Autowired
    public RecordProcessor(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    EventService eventService;

    @Autowired
    private EventDao eventDao;


    public void process(LogRecord logRecord, Map<String, Map<State,Long>> recordMap){

        long  calDuration =0L;
        boolean  alertFlag =Boolean.FALSE;

        // Check if the record exit already in Map
        if(isRecordPresent(logRecord,recordMap) && isHavingBothRecordEntryPresent(logRecord,recordMap)) {
            calDuration= getTimeDiff(logRecord,recordMap);

            // Set Alert Flag if Duration is more than 4 ms
            alertFlag = calDuration > 4 ? Boolean.TRUE:Boolean.FALSE;

            // call persistence layer - to persist in DB - ID, timeDiff etc
            EventService eventService = new EventService(eventDao);
            eventService.saveEvent(createEventDB(logRecord,calDuration,alertFlag));

            // remove from hashMap to save space
            deleteRecord(logRecord,recordMap);
        }else{
            // Add entry in Map - as It is not present already in Map
            addRecord(logRecord,recordMap);
        }
    }

    public void displayAllEvents(){

        EventService eventService = new EventService(eventDao);
        eventService.findAllEvents().stream().forEach(System.out::println);

    }

    private Event createEventDB(LogRecord logRecord, long calDuration, boolean alertFlag) {

        Event event = new Event();
        event.setId(logRecord.getId());
        event.setDuration(calDuration);
        event.setHost(logRecord.getHost());
        event.setType(logRecord.getType());
        event.setAlertFlag(alertFlag);
        return event;
    }

    private boolean isRecordPresent(LogRecord logRecord,Map<String, Map<State,Long>> recordMap){
        return recordMap.containsKey(logRecord.getId());
    }

    private boolean isHavingBothRecordEntryPresent(LogRecord logRecord,Map<String, Map<State,Long>> recordMap){
        long zeroNumber = 0L;
        return !recordMap.get(logRecord.getId()).entrySet().stream().anyMatch(e -> e.getKey().equals(logRecord.getState()));
    }

    private void addRecord(LogRecord logRecord,Map<String, Map<State,Long>> recordMap){
        Map<State, Long> tempInternalMap = new HashMap<>();
        tempInternalMap.put(logRecord.getState(),logRecord.getTimestamp());
        recordMap.put(logRecord.getId(),tempInternalMap);;
    }

    private void deleteRecord(LogRecord logRecord,Map<String, Map<State,Long>> recordMap){
        recordMap.remove(logRecord.getId());
    }

    private long getTimeDiff(LogRecord logRecord,Map<String, Map<State,Long>> recordMap) {
        long timeDiff = 0L;
        Optional<Long> existingTimeStamp = recordMap.get(logRecord.getId()).entrySet().stream().map(e -> e.getValue()).distinct().findFirst();
        return Math.abs( existingTimeStamp.get() - logRecord.getTimestamp());
    }

}
