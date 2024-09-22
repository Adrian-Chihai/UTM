package org.example.broker;

import org.example.common.ConnectionInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.Payload;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;

public class PayloadHandler {
    static ObjectMapper mapper = new ObjectMapper();

    public static void handle(byte[] data, ConnectionInfo<AsynchronousSocketChannel> connectionInfo) {
        String payloadString = new String(data, StandardCharsets.UTF_8);

        if (payloadString.startsWith("subscribe#")) {

        } else {
            try {
                Payload payload = mapper.readValue(data, Payload.class);
                PayloadStorage.add(payload);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
