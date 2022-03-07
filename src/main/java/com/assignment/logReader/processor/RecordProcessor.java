package com.assignment.logReader.processor;

import com.assignment.logReader.models.LogRecord;
import com.assignment.logReader.models.State;
import com.assignment.logReader.repository.EventService;
import com.assignment.logReader.repository.dbModel.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class RecordProcessor {

    @Autowired
    EventService eventService;

    Map<String, Map<State,Long>> recordMap = new ConcurrentHashMap<>();

    public void process(LogRecord logRecord){

        long  calDuration =0L;
        boolean  alertFlag =Boolean.FALSE;

        // Check if the record exit already in Map
        if(isRecordPresent(logRecord) && isHavingBothRecordEntryPresent(logRecord)) {
            calDuration= getTimeDiff(logRecord);

            // Set Alert Flag if Duration is more than 4 ms
            alertFlag = calDuration > 4 ? Boolean.TRUE:Boolean.FALSE;

            // call persistence layer - to persist in DB - ID, timeDiff etc
            eventService.saveEvent(createEventDB(logRecord,calDuration,alertFlag));

            // remove from hashMap to save space
            deleteRecord(logRecord);
        }else{
            // Add entry in Map - as It is not present already in Map
            addRecord(logRecord);
        }
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

    private boolean isRecordPresent(LogRecord logRecord){
        return recordMap.entrySet().contains(logRecord.getId());
    }

    private boolean isHavingBothRecordEntryPresent(LogRecord logRecord){
        long zeroNumber = 0L;
        return recordMap.get(logRecord.getId()).entrySet().stream().filter(e -> e.getKey().equals(logRecord.getState())).count() != zeroNumber;
    }

    private void addRecord(LogRecord logRecord){
        Map<State, Long> tempInternalMap = new HashMap<>();
        tempInternalMap.put(logRecord.getState(),logRecord.getTimestamp());
        recordMap.put(logRecord.getId(),tempInternalMap);;
    }

    private void deleteRecord(LogRecord logRecord){
        recordMap.remove(logRecord.getId());
    }

    private long getTimeDiff(LogRecord logRecord) {
        long timeDiff = 0L;
        Optional<Long> existingTimeStamp = recordMap.get(logRecord.getId()).entrySet().stream().map(e -> e.getValue()).distinct().findFirst();
        return Math.abs( existingTimeStamp.get() - logRecord.getTimestamp());
    }

}
