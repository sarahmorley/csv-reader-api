
This is a REST API that can take in a query parameter and do the following:

Read a CSV file from a folder.
Based on the parameter it inserts either the odd or even CSV rows into AWS DynamoDB.
If a parameter is not passed it inserts all the CSV rows into AWS DynamoDB.
If then returns the requested CSV rows (all, even, or odd rows based on parameter) as a String Array.

## Assumptions and Approach
I decided to use DynamoDB local as the database store.



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
Maven Surefire can be used to run the tests with the following command
```
mvn clean test
```


