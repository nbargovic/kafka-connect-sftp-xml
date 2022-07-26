# kafka-connect-sftp-xml
XML File format extension for SFTP Sink Connector

This SFTP Sink File Formatter will:
- read a string from a kafka topic
- test if the string is valid xml
- write the xml string to a file with the .xml extension

This SFTP Sink File Formatter will NOT:
- apply schema to the xml
- validate the schema of the xml

To build:
`./gradlew jar`

To Deploy:
https://docs.confluent.io/kafka-connect-sftp/current/sink-connector/index.html#sftp-file-formats