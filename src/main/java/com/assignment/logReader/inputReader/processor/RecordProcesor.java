package com.assignment.logReader.inputReader.processor;

import com.assignment.logReader.models.LogRecord;
import com.assignment.logReader.models.State;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class RecordProcesor {

    Map<String, Map<State,Long>> recordMap = new ConcurrentHashMap<>();

    public void process(LogRecord logRecord){

        addRecord(logRecord);

        if(isRecordPresent(logRecord) & havingBothRecordEntryPresent(logRecord))
            getTimeDiff(logRecord);

        // call persistance leverl - to persist  - ID, timeDiff etc
    }

    private boolean isRecordPresent(LogRecord logRecord){
        return recordMap.entrySet().contains(logRecord.getId());
    }

    private boolean havingBothRecordEntryPresent(LogRecord logRecord){
        Map<State, Long> internalMap = recordMap.get(logRecord.getId());

        Optional<Long> optStarted = Optional.of(internalMap.get(State.STARTED));
        Optional<Long> optFinished = Optional.of(internalMap.get(State.FINISHED));

        if(optFinished.isPresent() && optStarted.isPresent())
            return true;
        else
            return false;
    }


    private void addRecord(LogRecord logRecord){
        Map<State, Long> tempInternalMap = new HashMap<>();
        tempInternalMap.put(logRecord.getState(),logRecord.getTimestamp());
        recordMap.put(logRecord.getId(),tempInternalMap);;
    }


    private Long getTimeDiff(LogRecord logRecord) {
        
        Long timeDiff = 0L;
            Map<State, Long> internalMap = recordMap.get(logRecord.getId());

            Optional<Long> optStarted = Optional.of(internalMap.get(State.STARTED));
            Optional<Long> optFinished = Optional.of(internalMap.get(State.FINISHED));

            if(optFinished.isPresent() && optStarted.isPresent()) {

               timeDiff =  optFinished.get() - optStarted.get();
               // Call persistence layer
               recordMap.remove(logRecord.getId());
            }
        return timeDiff;
    }

}
