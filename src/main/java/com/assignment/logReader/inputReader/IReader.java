package com.assignment.logReader.inputReader;

import com.assignment.logReader.exception.CustomException;
import com.assignment.logReader.models.LogRecord;

import java.io.IOException;

public interface IReader {

    void read(String inputFile) throws IOException, CustomException;
}
