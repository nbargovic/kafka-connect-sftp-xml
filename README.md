# kafka-connect-sftp-xml
XML File format extension for SFTP Sink Connector

This SFTP Sink File Formatter will:
- read a string from a kafka topic
- test if the string is valid xml
- write the xml string to a file with the .xml extension

This SFTP Sink File Formatter will NOT:
- apply schema to the xml
- validate the schema of the xml

To Build:

Requires JDK 1.8 to build the jar (example: java-1.8.0-openjdk-devel)

Run: `./gradlew clean jar`

Jar file will be generated in `./build/libs/kafka-connect-sftp-xml-1.0.0.jar`

To Deploy:

https://docs.confluent.io/kafka-connect-sftp/current/sink-connector/index.html#sftp-file-formats