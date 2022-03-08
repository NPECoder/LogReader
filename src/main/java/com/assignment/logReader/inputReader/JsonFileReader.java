package com.assignment.logReader.inputReader;

import com.assignment.logReader.exception.CustomException;
import com.assignment.logReader.models.State;
import com.assignment.logReader.processor.RecordProcessor;
import com.assignment.logReader.models.LogRecord;
import com.assignment.logReader.repository.EventService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JsonFileReader implements IReader{

    private final Logger logger = LoggerFactory.getLogger(JsonFileReader.class);
    @Autowired
    Gson gson;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    RecordProcessor recordProcessor;

    @Autowired
    public JsonFileReader(RecordProcessor recordProcessor) {

        this.recordProcessor = recordProcessor;
    }

    @Autowired
    EventService eventService;


  /*  public LogRecord read(String inputFile) throws IOException {

        Gson gson = new Gson();
        Resource resource = new ClassPathResource(inputFile);
        LogRecord logRecord = gson.fromJson(new FileReader(resource.getFile()), LogRecord.class);

        System.out.println(logRecord.toString());
        return logRecord;
    }
*/
  @Override
    public void read(String path) throws CustomException {
        logger.info(" JSON Read() : Started :  File Name/path : "+path);
        Map<String, Map<State,Long>> recordMap = new ConcurrentHashMap<>();
        Resource resource = new ClassPathResource(path);
        try (InputStream inputStream = Files.newInputStream(Paths.get(resource.getURI()))) {
            try (JsonReader reader = new JsonReader(new InputStreamReader(inputStream))) {
                reader.beginArray();
                logger.info(" Started reading file line by line: ");
                while (reader.hasNext()) {
                    StopWatch stopWatchProcess = new StopWatch("Process Record");
                    stopWatchProcess.start();

                    logger.info("Convert JSON to POJO : Started");
                    LogRecord logRecord = new Gson().fromJson(reader, LogRecord.class);
                    logger.info("Convert JSON to POJO : Completed " );
                    logger.info("POJO Object: " + logRecord.toString() );

                    RecordProcessor recordProcessor = new RecordProcessor(eventService);
                    recordProcessor.process(logRecord,recordMap);
                    stopWatchProcess.stop();
                    logger.info(" Processing took Per Record: " + stopWatchProcess.getTotalTimeSeconds() + " seconds");

                }
                reader.endArray();

            } catch (IOException e) {
                    throw new CustomException("Error Occurred file reading the Input File");
            }
        } catch (IOException | CustomException e) {
            throw new CustomException("Error Occurred file reading the Input File");
        }
    }
}
