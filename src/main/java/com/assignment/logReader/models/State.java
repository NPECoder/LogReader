package com.assignment.logReader.models;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public enum State {

    STARTED, FINISHED;

    private static Map<String, State> lookup = new HashMap<>();

    static{
        lookup = Arrays.stream(State.values()).collect(Collectors.toMap( e -> e.name(), e-> e));
    }

    public static State getInstance(String name){
        return lookup.get(name);
    }

}
