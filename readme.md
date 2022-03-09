#[[#]]# ${LogReader}

Project description: 
Our custom-build server logs different events to a file named logfile.txt.  Every event has 2 entries in the file
- one entry when the event was started and another when the event was finished. The entries in the file
  have no specific order (a finish event could occur before a start event for a given id)

- Every line in the file is a JSON object containing the following event data:
    id - the unique event identifier
    state - whether the event was started or finished (can have values "STARTED" or "FINISHED"
    timestamp - the timestamp of the event in milliseconds
  Application Server logs also have the following additional attributes:
    type - type of log
    host - hostname
  
  Example contents of logfile.txt:
-  {"id": "xyz", "state": "STARTED", "type": "APPLICATION_LOG", "host": 1234, "timestamp": 342354545787},
   {"id": "123", "state": "STARTED",  "timestamp": 342354545787},
   {"id": "xyz", "state": "FINISHED", "type": "APPLICATION_LOG", "host": 1234, "timestamp": 342354545711},
   {"id": "123", "state": "FINISHED", "timestamp": 342354545780},
   
  In the example above, the event xyz duration is 342354545787 - 342354545711 = 76ms
  The longest event is 123 (342354545787 - 342354545780 = 7ms)

#[[##]]# Design Consideration Taken
# Use of Object-Oriented Approach 
- Used the Interface to implemented loose coupling  like IReader to implemented any kind of reader in future ( JSON, XML, etc)

# Large JSON file  handling
- Reading the JSON line by line and processing each line to aviod load of Memory - while using large JSON file.

# Multi Threading implementation
- Used the Concurrent HashMap to handle the multi threading issues.

# Logging and StopWatch
- Add logging statement and used Stopwatch to calculate the processed time of application , process, step.

# Test Cases
- Add few test cases due to limited time limit


## How to run the Program 
- Place the required input file and replace it with existing file - "log.json"
- Run the application using Run from Spring boot Main class - "LogReaderApplication.java"
- log will display having all processing steps and details.


## Running Tests
- Only one test class is created so - Run the  class - "JsonReaderTest.java" using run button. 
- 2 test will run - one +ve case and 2nd one Negative case

Spring Application Sucess log and Screenshot - as follows:
![image](https://user-images.githubusercontent.com/101072717/157356797-bf3c5517-f00c-4f54-aa6e-3ab4e5778f40.png)

[Application_Sucess_Log.txt](https://github.com/NPECoder/LogReader/files/8210969/Application_Sucess_Log.txt)

