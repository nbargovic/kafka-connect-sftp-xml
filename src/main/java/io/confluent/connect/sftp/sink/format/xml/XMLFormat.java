package io.confluent.connect.sftp.sink.format.xml;

import io.confluent.connect.sftp.sink.SftpSinkConnectorConfig;
import io.confluent.connect.sftp.sink.storage.SftpSinkStorage;
import io.confluent.connect.storage.format.Format;
import io.confluent.connect.storage.format.RecordWriterProvider;
import io.confluent.connect.storage.format.SchemaFileReader;

import java.util.HashMap;
import java.util.Map;

public class XMLFormat implements Format<SftpSinkConnectorConfig, String> {

    private final SftpSinkStorage storage;

    public XMLFormat(SftpSinkStorage storage) {
        this.storage = storage;
        Map<String, Object> converterConfig = new HashMap<>();
        converterConfig.put("schemas.enable", "false");
    }

    @Override
    public RecordWriterProvider<SftpSinkConnectorConfig> getRecordWriterProvider() {
        return new XMLRecordWriterProvider(this.storage);
    }

    /**
     * This method provides SchemaFileReader.
     * This is currently not supported as we don't need this functionality for now.
     *
     * @throws UnsupportedOperationException if invoked.
     */
    @Override
    public SchemaFileReader<SftpSinkConnectorConfig, String> getSchemaFileReader() {
        throw new UnsupportedOperationException("Reading schemas from sftp is currently not supported");
    }

    /**
     * This method provides HiveFactory.
     * This is currently not supported as we don't need this functionality for now.
     *
     @throws UnsupportedOperationException if invoked.
     */
    @Override
    public Object getHiveFactory() {
        throw new UnsupportedOperationException(
                "Hive integration is currently not supported in sftp Connector"
        );
    }
}
