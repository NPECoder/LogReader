package com.assignment.logReader;

import com.assignment.logReader.exception.CustomException;
import com.assignment.logReader.inputReader.JsonFileReader;
import com.assignment.logReader.processor.RecordProcessor;
import com.assignment.logReader.models.LogRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan
public class LogReaderApplication {


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
		JsonFileReader jsonFileReader = new JsonFileReader(recordProcessor);
		//LogRecord logRecord = jsonFileReader.read(inputFile);
		try {
			jsonFileReader.readJson(inputFile);
		} catch (CustomException e) {
			throw new CustomException("Error-100 : Error occurred file processing Records");
		}
	}

}
