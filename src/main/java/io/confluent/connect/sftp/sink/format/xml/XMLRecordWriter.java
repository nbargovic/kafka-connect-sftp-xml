package io.confluent.connect.sftp.sink.format.xml;

import io.confluent.connect.sftp.sink.storage.SftpSinkStorage;
import io.confluent.connect.storage.format.RecordWriter;
import org.apache.kafka.connect.sink.SinkRecord;
import java.io.IOException;

public class XMLRecordWriter implements RecordWriter {

    XMLRecordWriter(final SftpSinkStorage storage, final String filename) {
        // stub
    }

    /**
     * This method writes record to outputstream.
     * @param record Represents SinkRecord.
     */
    @Override
    public void write(SinkRecord record) {
        // stub
    }

    /**
     * This method commits the record and closes the writer.
     */
    @Override
    public void commit() {
        //
    }

    /**
     * This method closes the writer.
     */
    @Override
    public void close() {
        //
    }
}
