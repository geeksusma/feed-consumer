package com.sparta.provider.load;

import com.sparta.core.ChecksumFailedException;
import com.sparta.core.Deserializer;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;


//TODO a posibble refactor could be to have some different implementations of the Deserializer, the problem is how
//to share the byteBuffer
@Component
public class DeserializeProviderImpl implements Deserializer<Provider> {

    private String providerName;
    private ByteBuffer content;

    @Override
    public void initialize(String providerName, byte[] content) {
        this.providerName = providerName;
        this.content = ByteBuffer.wrap(content);
    }

    @Override
    public Provider deserialize() {
        //should throw illegal exception is it was not initialized before TODO

        long recordsNumber = 0;
        List<Record> records = null;

        if (content != null && content.hasRemaining()) {
            recordsNumber = readNextAsLong();
            records = new ArrayList<>((int) recordsNumber);

            for (int i = 0; i < recordsNumber; i++) {
                records.add(readNextAsRecord());
            }
        }
        return new Provider(providerName, recordsNumber, records);
    }


    private Record readNextAsRecord() {
        List<Sensor> sensors = null;
        final long index = readNextAsLong();
        final long timestamp = readNextAsLong();
        final String city = readNextAsString();

        final int numBytesSensor = readNextAsInt();

        if (numBytesSensor > 0) {

            //I don't like so much,
            assertChecksum(numBytesSensor);

            final int numSensors = readNextAsInt();
            if (numSensors > 0) {
                sensors = new ArrayList<>(numSensors);
                for (int i = 0; i < numSensors; i++)
                    sensors.add(readNextAsSensor());
            }

            skipCRCBloc();
        }

        return new Record(index, timestamp, city, sensors);
    }

    private void assertChecksum(int numBytesToCheck) {
        final Checksum checksum = new CRC32();
        final int position = content.position();
        final byte[] sensorData = new byte[numBytesToCheck];

        content.get(sensorData);
        checksum.update(sensorData, 0, sensorData.length);

        final long checksumFromContent = readNextAsLong();

        content.position(position);

        if (checksum.getValue() != checksumFromContent) {
            throw new ChecksumFailedException("Invalid checksum!");
        }
    }

    private Sensor readNextAsSensor() {
        final String id = readNextAsString();
        final int measure = readNextAsInt();

        return new Sensor(id, measure);
    }

    private void skipCRCBloc() {
        readNextAsLong();
    }

    private String readNextAsString() {

        int stringSize = readNextAsInt();
        if (stringSize == 0) {
            return null;
        }

        byte[] string = new byte[stringSize];
        content.get(string);
        return new String(StandardCharsets.UTF_8.decode(ByteBuffer.wrap(string)).array());
    }

    private int readNextAsInt() {
        return content.getInt();
    }

    private long readNextAsLong() {
        return content.getLong();
    }

}
