package com.assignment.logReader;

import com.assignment.logReader.exception.CustomException;
import com.assignment.logReader.inputReader.JsonFileReader;
import com.assignment.logReader.processor.RecordProcessor;
import com.assignment.logReader.models.LogRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StopWatch;

import java.io.IOException;

@SpringBootApplication
@ComponentScan
public class LogReaderApplication {


	private static final Logger logger = LoggerFactory.getLogger(LogReaderApplication.class);

	private static String inputFile;

	@Autowired
	private static JsonFileReader jsonFileReader;
	private static RecordProcessor recordProcessor;

	@Value("${com.assignment.logReader.logFileName}")
		public void setInputFile(String value) {
		this.inputFile = value;
	}


	@Autowired
	public void setRecordProcessor(){
		this.recordProcessor = recordProcessor;
	}

	@Autowired
	public void setJsonFileReader(){
		this.jsonFileReader = jsonFileReader;
	}

	public static void main(String[] args) throws CustomException {
		SpringApplication.run(LogReaderApplication.class, args);

		logger.info("Application Started");
		StopWatch sw = new StopWatch("Whole Application - Umbrella stopWatch");
		sw.start();
		JsonFileReader jsonFileReader = new JsonFileReader(recordProcessor);
		try {
			jsonFileReader.read(inputFile);
			sw.stop();
			logger.info("Application took total: " + sw.getTotalTimeSeconds() + " seconds");
		} catch (CustomException e) {
			logger.info("Error occurred at Start of Application : " + e.getMessage());
			throw new CustomException("Error-001 : Error occurred file processing Records" + e.getMessage());

		}
	}

}
