package com.assignment.logReader.InputReader;

import com.assignment.logReader.models.LogRecord;

import java.io.IOException;

public interface IReader {

    LogRecord read(String inputFile) throws IOException;
}
