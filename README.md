# Csv-Reader-Api #
This is a REST API that can take in a query parameter and do the following:

Read a CSV file from a folder.
Based on the parameter it inserts either the odd or even CSV rows into AWS DynamoDB.
If a parameter is not passed it inserts all the CSV rows into AWS DynamoDB (local version).
It then returns the requested CSV rows (all, even, or odd rows) as a String Array.

The CSV file that the API reads is set in the application.properties 
It is currently set as:
```
file.path = /PracticeFile.csv
```

## Technologies used
*  Java 8
*  Spring Boot
*  Maven
*  AWS DynamoDB local

## Build and Run
### Run AWS DynamoDb locally

Download DynamoDB local from the following link:
https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html

After you download the archive, extract the contents and copy the extracted directory to a location of your choice.

To start DynamoDB on your computer, open a command prompt window, navigate to the directory where you extracted DynamoDBLocal.jar, and enter the following command.
```
java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
```
Once dynamoDB local is running you can query and list tables using the local shell @
```
http://localhost:8000/shell/
```

### Build and Run the csv-reader-api
```
mvn clean package
```
The built java jar can then be found in the target folder. CD to target and run it using the following command


```
java -jar csv-reader-api-0.0.1-SNAPSHOT.jar
```

The api can also be run locally in IntelliJ

## Usage

Once the api is built and running it can be hit at the following URL
```
http://localhost:8080/rows
```
It can take a 'parity' query parameter value. This value can be 'ODD', 'EVEN' or ''.
It can also work without passing any query parameter.

An example of the URL with a query parameter

```
http://localhost:8080/rows?parity=ODD
```


## Testing

### Unit testing
Maven Surefire can be used to run the units tests with the following command
```
mvn clean test
```
The TestCsvReader class overrides the 'file.name' value in the application.properties

and points to a test csv called TestFiles.csv. 


### Integration testing
Maven Failsafe can be used to run the integration tests with the following command
```
mvn clean verify
```
This command will 
* Download and unpack DynamoDb Local to disk 
* Run DynamoDB local
* Execute the application jar
* Run the integration tests


## Logging

Logging set up using LogBack



