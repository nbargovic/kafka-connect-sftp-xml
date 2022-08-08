package io.confluent.connect.sftp.sink.format.xml;

import io.confluent.connect.sftp.sink.storage.SftpOutputStream;
import io.confluent.connect.sftp.sink.storage.SftpSinkStorage;
import io.confluent.connect.storage.format.RecordWriter;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.runtime.ConnectorConfig;
import org.apache.kafka.connect.runtime.errors.ToleranceType;
import org.apache.kafka.connect.sink.SinkRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

public class XMLRecordWriter implements RecordWriter {

    private static final Logger log = LoggerFactory.getLogger(XMLRecordWriter.class);
    private final OutputStreamWriter writer;
    private final SftpOutputStream sftpOut;
    private final boolean ignoreErrors;

    XMLRecordWriter(final SftpSinkStorage storage, final String filename) throws IOException {
        this.sftpOut = storage.create(filename, true);
        this.writer  = new OutputStreamWriter(this.sftpOut);
        String errorTolerance = storage.conf().originalsStrings().get(ConnectorConfig.ERRORS_TOLERANCE_CONFIG).toLowerCase();
        this.ignoreErrors = errorTolerance.equals(ToleranceType.ALL.value());
    }

    /**
     * This method writes record to outputstream.
     * @param record Represents SinkRecord.
     */
    @Override
    public void write(SinkRecord record) {
        String message = record.value().toString();
        boolean writeMsg = true;
        try {
            DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(message)));
        } catch (Exception e) {
            String errorMsg = "Message is not valid XML: topic=[" + record.topic() + "], partition=[" + record.kafkaPartition() + "], offset=[" + record.kafkaOffset() + "]";
            if(ignoreErrors) {
                log.error("Skipping message. " + errorMsg);
                writeMsg = false;
            }
            else{
                //This will fail the connector task.
                throw new InvalidRecordException(errorMsg);
            }
        }
        if(writeMsg) {
            try {
                writer.write(message);
                writer.write(System.getProperty("line.separator"));
            } catch (Exception e) {
                throw new ConnectException("Writing records failed", e);
            }
        }
    }

    /**
     * This method commits the record and closes the writer.
     */
    @Override
    public void commit() {
        try {
            writer.flush();
            sftpOut.commit();
        } catch (IOException e) {
            throw new ConnectException("Record commit failed", e);
        }
    }

    /**
     * This method closes the writer.
     */
    @Override
    public void close() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                throw new ConnectException("Close of record stream failed", e);
            }
        }
    }
}
