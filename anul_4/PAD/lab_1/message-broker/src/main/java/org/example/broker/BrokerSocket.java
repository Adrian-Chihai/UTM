package org.example.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.ConnectionInfo;
import org.example.common.Payload;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BrokerSocket {
    private AsynchronousServerSocketChannel serverSocket;
    private final Map<String, List<AsynchronousSocketChannel>> subscribers = new ConcurrentHashMap<>();
    private final Map<String, List<String>> clientTopics = new ConcurrentHashMap<>(); // Key is now String (clientId)
    private final Map<String, AsynchronousSocketChannel> clientSockets = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String storageFile = ".\\subscribers.json"; // File to store subscriber data

    public BrokerSocket() throws IOException {
        serverSocket = AsynchronousServerSocketChannel.open();
        loadSubscribersFromFile(); // Load existing subscribers
    }

    public void start(String ip, int port) throws IOException {
        InetSocketAddress hostAddress = new InetSocketAddress(ip, port);
        serverSocket.bind(hostAddress);
        System.out.println("Server started on " + ip + ":" + port);
        acceptConnection();
    }

    private void acceptConnection() {
        serverSocket.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Void attachment) {
                System.out.println("Accepted a connection!");

                ConnectionInfo<AsynchronousSocketChannel> connectionInfo = new ConnectionInfo<>(1024);
                connectionInfo.setSocket(result);
                try {
                    connectionInfo.setAddress(result.getRemoteAddress().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handleClient(connectionInfo);
                acceptConnection();
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("Failed to accept a connection: " + exc.getMessage());
            }
        });
    }

    private void handleClient(ConnectionInfo<AsynchronousSocketChannel> connectionInfo) {
        AsynchronousSocketChannel clientChannel = connectionInfo.getSocket();
        ByteBuffer buffer = ByteBuffer.allocate(connectionInfo.BUFF_LEN);

        final CompletionHandler<Integer, ByteBuffer> readHandler = new CompletionHandler<>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if (result == -1) {
                    System.out.println("Connection closed by client.");
                    clientTopics.remove(clientChannel); // Remove client on disconnect
                    try {
                        saveSubscribersToFile(); // Save on disconnect
                        clientChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                buffer.flip();
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);
                System.out.println("Received: " + new String(data));

                handleMessage(data, connectionInfo);

                buffer.clear();
                clientChannel.read(buffer, buffer, this);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer buffer) {
                System.out.println("Failed to read data: " + exc.getMessage());
            }
        };

        clientChannel.read(buffer, buffer, readHandler);
    }

    private void handleMessage(byte[] data, ConnectionInfo<AsynchronousSocketChannel> connectionInfo) {
        String message = new String(data);
        if (message.startsWith("subscribe#")) {
            String[] parts = message.split("#");
            String topic = parts[1];
            String clientId = parts[2];
            subscribeClientToTopic(connectionInfo.getSocket(), topic, clientId);
        } else {
            forwardMessageToSubscribers(data);
        }
    }

    private void subscribeClientToTopic(AsynchronousSocketChannel client, String topic, String clientId) {
        // Store the clientId-topic relationship
        clientTopics.computeIfAbsent(clientId, k -> new ArrayList<>()).add(topic);

        // Update the client socket in case of reconnection
        clientSockets.put(clientId, client);

        // Add the client to the subscribers list for the topic
        subscribers.computeIfAbsent(topic, k -> new ArrayList<>()).add(client);

        System.out.println("Client " + clientId + " subscribed to topic: " + topic);
        saveSubscribersToFile(); // Save subscription data to file
    }

    private void forwardMessageToSubscribers(byte[] data) {
        try {
            Payload payload = new ObjectMapper().readValue(data, Payload.class);
            List<AsynchronousSocketChannel> clients = subscribers.getOrDefault(payload.getTopic(), Collections.emptyList());

            for (AsynchronousSocketChannel client : clients) {
                client.write(ByteBuffer.wrap(data), client, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
                    @Override
                    public void completed(Integer result, AsynchronousSocketChannel attachment) {
                        System.out.println("Message forwarded to subscriber.");
                    }

                    @Override
                    public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
                        System.out.println("Failed to forward message: " + exc.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveSubscribersToFile() {
        Map<String, List<String>> saveData = new HashMap<>();

        // Use the clientId as the key
        clientTopics.forEach((clientId, topics) -> {
            saveData.put(clientId, topics);  // Use clientId instead of client.getRemoteAddress()
        });

        try {
            String jsonData = objectMapper.writeValueAsString(saveData);
            try (FileWriter fileWriter = new FileWriter(storageFile)) {
                fileWriter.write(jsonData);
                System.out.println("Subscriber data saved to file.");
            }
        } catch (IOException e) {
            System.err.println("Failed to save subscribers to file: " + e.getMessage());
        }
    }

    private void loadSubscribersFromFile() {
        try {
            if (Files.exists(Paths.get(storageFile))) {
                String jsonData = new String(Files.readAllBytes(Paths.get(storageFile)));
                Map<String, List<String>> loadedData = objectMapper.readValue(jsonData,
                        objectMapper.getTypeFactory().constructMapType(Map.class, String.class, List.class));

                for (Map.Entry<String, List<String>> entry : loadedData.entrySet()) {
                    String clientId = entry.getKey();
                    List<String> topics = entry.getValue();

                    System.out.println("Loaded subscriber: " + clientId + " for topics: " + topics);

                    clientTopics.put(clientId, topics); // Rebuild the client-topic map
                }
                System.out.println("Subscriber data loaded from file.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load subscribers from file: " + e.getMessage());
        }
    }
}
