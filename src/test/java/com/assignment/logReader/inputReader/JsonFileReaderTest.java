package com.assignment.logReader.inputReader;


import com.assignment.logReader.exception.CustomException;
import com.assignment.logReader.processor.RecordProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class JsonFileReaderTest {

    @Autowired
    RecordProcessor recordProcessor;

    @Test
    void blankOrNotExistFileName() throws CustomException {
        JsonFileReader jsonFileReader = new JsonFileReader(recordProcessor);
        Exception exception = assertThrows(Exception.class, () -> jsonFileReader.read("DoesNotExistFile.json"));
        assertEquals("Error Occurred file reading the Input File", exception.getMessage());
    }

    @Test
    void successProcessing() throws CustomException {
        JsonFileReader jsonFileReader = new JsonFileReader(recordProcessor);
        assertDoesNotThrow(() ->jsonFileReader.read("log.json"));
    }
}
