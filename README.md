# PhData Challenge
The customer runs a website and periodically is attacked by a botnet in a Distributed Denial of Service (DDOS) attack. You’ll be given a log file in Apache log format from a given attack. Use this log to build a simple real-time detector of DDOS attacks. 

#### Requirements
- Ingest
    - Read a file from local disk and write to a message system such as Kafka.
 - Detection
   - Write an application which reads messages from the message system and detects whether the attacker is part of the DDOS attack
   - Once an attacker is found, the ip-address should be written to a results directory which could be used for further processing
   - An attack should be detected one to two minutes after starting

#### Tech

* Spring Boot
* Spring Kafka
* Spring AWS
* MySql

#### Running the application 

Install Kafka and start a zookeeper server for Kafka.

```sh
$ cd your-kafka-directory
$ bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start the Kafka server

```sh
$ bin/kafka-server-start.sh config/server.properties
```

Run the spring app

```sh
$ gradle bootRun
```
#### Using the app
Use the following curl statement or use Postman to start processing
 - read log file and sent it to kafka 
 - read the messages from kafka and find the attackers 
 - store all results to MySql database 
 - generate a Json file and uploade it to S3 bucket. 
 
```
curl -X POST \
  http://localhost:9000/api/kafka \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:9000' \
  -H 'Postman-Token: 05f74d10-21dc-43e2-bfd1-145fa889d29f,8ed9ecf2-04aa-4229-ba59-e124a6a2b9ab' \
  -H 'User-Agent: PostmanRuntime/7.13.0' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache' \
  -H 'content-length: '
```

Use the following curl statement or Postman to retrive all apacheLogs in MySql database
```
curl -X GET \
  http://localhost:9000/api/kafka/all/apacheLogs \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 55130f5b-3b57-4d08-9790-615406517825' \
  -H 'cache-control: no-cache'
```
Use the following curl statement or Postman to retrive all attackers in MySql database
```
curl -X GET \
  http://localhost:9000/api/kafka/all/attackers \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:9000' \
  -H 'Postman-Token: 73812af3-ef97-4b06-a794-e65cd7f7e6f0,4a385910-e077-442d-a982-2694e3839e56' \
  -H 'User-Agent: PostmanRuntime/7.13.0' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache'
```

Use the following curl statement or Postman to Store file into S3 bucket (Replace the file path to the file path you want to uploade)
```
curl -X POST \
  http://localhost:9000/storage/uploadFile \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'Host: localhost:9000' \
  -H 'Postman-Token: f5a49723-1d57-44fd-b201-6637a7ab37d9,5ac0a7f2-496c-45ae-8f25-c139e9170158' \
  -H 'User-Agent: PostmanRuntime/7.13.0' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache' \
  -H 'content-length: 24945' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -F file=@/EXAMPLE_FILE_PATH
```
Use the following curl statement or Postman to remove file from S3 bucket (Replace the file url to the file url you want to reomve)

```
curl -X DELETE \
  http://localhost:9000/storage/deleteFile \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Host: localhost:9000' \
  -H 'Postman-Token: e5f83e45-0bd9-48ac-9933-8f4472bbf162,f78a6d30-b789-49cb-9c75-f8d8e5e80112' \
  -H 'User-Agent: PostmanRuntime/7.13.0' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache' \
  -H 'content-length: 169' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -F url=EXAMPLE_URL
```
