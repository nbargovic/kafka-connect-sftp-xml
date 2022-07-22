package io.confluent.connect.sftp.sink.format.xml;

import io.confluent.connect.sftp.sink.SftpSinkConnectorConfig;
import io.confluent.connect.sftp.sink.storage.SftpSinkStorage;
import io.confluent.connect.storage.format.RecordWriter;
import io.confluent.connect.storage.format.RecordWriterProvider;

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
        return new XMLRecordWriter(storage,filename);
    }
}
