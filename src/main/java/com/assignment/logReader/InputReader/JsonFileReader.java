package com.assignment.logReader.InputReader;

import com.assignment.logReader.models.LogRecord;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;

@Service
public class JsonFileReader implements IReader{

@Autowired
    Gson gson;

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public LogRecord read(String inputFile) throws IOException {

        Gson gson = new Gson();
        Resource resource = new ClassPathResource(inputFile);
        LogRecord logRecord = gson.fromJson(new FileReader(resource.getFile()), LogRecord.class);

        System.out.println(logRecord.toString());
        return logRecord;
    }
}
