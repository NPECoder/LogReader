package com.assignment.logReader;

import com.assignment.logReader.inputReader.JsonFileReader;
import com.assignment.logReader.processor.RecordProcessor;
import com.assignment.logReader.models.LogRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class LogReaderApplication {


	private static String inputFile;

	@Value("${com.assignment.logReader.logFileName}")
		public void setInputFile(String value) {
		this.inputFile = value;
	}

	@Autowired
	static RecordProcessor recordProcesor;
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(LogReaderApplication.class, args);
		JsonFileReader jsonFileReader = new JsonFileReader(recordProcesor);
		LogRecord logRecord = jsonFileReader.read(inputFile);
	}

}
