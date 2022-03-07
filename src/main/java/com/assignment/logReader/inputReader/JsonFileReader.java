package com.assignment.logReader.inputReader;

import com.assignment.logReader.inputReader.processor.RecordProcesor;
import com.assignment.logReader.models.LogRecord;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class JsonFileReader implements IReader{

@Autowired
    Gson gson;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    RecordProcesor recordProcesor;

    public JsonFileReader(RecordProcesor recordProcesor) {

        this.recordProcesor = recordProcesor;
    }

    @Override
    public LogRecord read(String inputFile) throws IOException {

        Gson gson = new Gson();
        Resource resource = new ClassPathResource(inputFile);
        LogRecord logRecord = gson.fromJson(new FileReader(resource.getFile()), LogRecord.class);

        System.out.println(logRecord.toString());
        return logRecord;
    }

    private void readLargeJson(String path) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(path))) {
            try (JsonReader reader = new JsonReader(new InputStreamReader(inputStream))) {
                reader.beginArray();
                while (reader.hasNext()) {
                    LogRecord logRecord = new Gson().fromJson(reader, LogRecord.class);
                    recordProcesor.process(logRecord);
                }
                reader.endArray();
            }
        }
    }
}
