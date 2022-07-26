package io.confluent.connect.sftp.sink.format.xml;

import io.confluent.connect.sftp.sink.SftpSinkConnectorConfig;
import io.confluent.connect.sftp.sink.storage.SftpSinkStorage;
import io.confluent.connect.storage.format.RecordWriter;
import io.confluent.connect.storage.format.RecordWriterProvider;
import org.apache.kafka.connect.errors.ConnectException;

import java.io.IOException;

public class XMLRecordWriterProvider implements RecordWriterProvider<SftpSinkConnectorConfig> {
    private static final String EXTENSION = ".xml";
    private final SftpSinkStorage storage;

    XMLRecordWriterProvider(SftpSinkStorage storage) {
        this.storage = storage;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }

    @Override
    public RecordWriter getRecordWriter(final SftpSinkConnectorConfig conf, final String filename) {
        try {
          return new XMLRecordWriter(storage,filename);
        } catch (IOException e) {
          throw new ConnectException("Writing records failed",e);
        }
    }
}
