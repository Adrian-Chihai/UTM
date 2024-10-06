package org.example.subscriber;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class SubscriberSocket {
    private AsynchronousSocketChannel socketChannel;
    private final String topic;
    private String clientId;
    private List<String> subscriptions; // List of topics this subscriber is subscribed to
    private final String configFileName; // Filename for the subscriber's config
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SubscriberSocket(String topic) throws IOException {
        this.topic = topic;
        this.socketChannel = AsynchronousSocketChannel.open();
        this.configFileName = "src/main/resources/subscribers/subscriberConfig.json"; // Single config file for all subscribers
        loadConfig(); // Load existing config or create new one
    }

    public void connect(String ip, int port) throws InterruptedException {
        InetSocketAddress hostAddress = new InetSocketAddress(ip, port);
        CountDownLatch latch = new CountDownLatch(1);

        socketChannel.connect(hostAddress, null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                System.out.println("Subscriber connected to broker.");
                if (!subscriptions.contains(topic)) {
                    subscriptions.add(topic);
                    saveConfig(); // Save after adding the new subscription
                }
                sendSubscription(topic);
                latch.countDown();
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("Failed to connect to broker: " + exc.getMessage());
                latch.countDown();
            }
        });

        latch.await();
    }

    private void sendSubscription(String topic) {
        String message = "subscribe#" + topic + "#" + clientId;
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
        socketChannel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("Subscription sent for topic: " + topic + " with clientId: " + clientId);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Failed to send subscription: " + exc.getMessage());
            }
        });
    }

    private void loadConfig() {
        try {
            // Always generate a new clientId
            this.clientId = UUID.randomUUID().toString();

            subscriptions = new ArrayList<>();

            // Check if the config file exists to load subscriptions
            File configFile = new File(configFileName);
            if (configFile.exists()) {
                try (FileReader reader = new FileReader(configFile)) {
                    JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

                    // Ensure jsonObject is not null and has necessary fields
                    if (jsonObject != null) {
                        // Load subscriptions from the existing file
                        subscriptions = new ArrayList<>();
                        if (jsonObject.has("subscriptions")) {
                            JsonArray topicsArray = jsonObject.getAsJsonArray("subscriptions");
                            for (int i = 0; i < topicsArray.size(); i++) {
                                subscriptions.add(topicsArray.get(i).getAsString());
                            }
                        }
                        // Update the clientId in the JSON object
                        jsonObject.addProperty("clientId", clientId);
                        saveConfig(); // Update the config file with the new clientId
                        System.out.println("Loaded config from file: " + jsonObject);
                    } else {
                        System.err.println("Config file is empty or invalid.");
                    }
                }
            } else {
                // Create new config if file doesn't exist
                subscriptions = new ArrayList<>();
                saveConfig(); // Save the new config
                System.out.println("Generated new clientId: " + clientId);
            }
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("Invalid JSON format in config file: " + e.getMessage());
        }
    }


    private void saveConfig() {
        try (FileWriter writer = new FileWriter(configFileName)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("clientId", clientId);
            JsonArray topicsArray = new JsonArray();
            for (String topic : subscriptions) {
                topicsArray.add(topic);
            }
            jsonObject.add("subscriptions", topicsArray);
            gson.toJson(jsonObject, writer);
            System.out.println("Saved config to file: " + jsonObject);
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }

    public void startListening() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        readFromChannel(buffer);
    }

    private void readFromChannel(ByteBuffer buffer) {
        socketChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (result > 0) {
                    attachment.flip();
                    byte[] data = new byte[attachment.remaining()];
                    attachment.get(data);
                    String message = new String(data, StandardCharsets.UTF_8);
                    System.out.println("Received message: " + message);

                    attachment.clear();
                    readFromChannel(attachment); // Continue reading
                } else if (result == -1) {
                    System.out.println("Connection closed by broker.");
                    closeChannel();
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Failed to read data: " + exc.getMessage());
                closeChannel();
            }
        });
    }

    private void closeChannel() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            System.err.println("Failed to close socket: " + e.getMessage());
        }
    }
}
