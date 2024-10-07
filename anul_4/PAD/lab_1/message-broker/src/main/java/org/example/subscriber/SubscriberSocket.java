package org.example.subscriber;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class SubscriberSocket {
    private AsynchronousSocketChannel socketChannel;
    private final List<String> subscriptions;
    private int reconnectAttempts = 0;

    public SubscriberSocket() throws IOException {
        this.socketChannel = AsynchronousSocketChannel.open();
        this.subscriptions = new ArrayList<>();
    }

    public boolean isConnected() {
        return socketChannel != null && socketChannel.isOpen();
    }

    public void connect(String ip, int port) throws InterruptedException {
        InetSocketAddress hostAddress = new InetSocketAddress(ip, port);
        CountDownLatch latch = new CountDownLatch(1);

        socketChannel.connect(hostAddress, null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                System.out.println("Subscriber connected to broker.");
                reconnectAttempts = 0; // Reset attempts on successful connection
                sendAllSubscriptions(); // Resend all subscriptions upon successful connection
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

    public void subscribe(String topic) {
        if (!subscriptions.contains(topic)) {
            subscriptions.add(topic); // Add the topic to the subscriptions list if not already subscribed
            sendSubscription(topic);  // Send the subscription to the broker
        } else {
            System.out.println("Already subscribed to topic: " + topic);
        }
    }

    private void sendAllSubscriptions() {
        for (String topic : subscriptions) {
            sendSubscription(topic); // Resubscribe to all topics after reconnecting
        }
    }

    private void sendSubscription(String topic) {
        if (!isConnected()) {
            System.out.println("Cannot send subscription. Subscriber is not connected.");
            return;
        }

        String message = "subscribe#" + topic + "\n"; // Add a newline to delimit each message
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
        socketChannel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("Subscription sent for topic: " + topic);
                attachment.clear(); // Clear the buffer to avoid concatenating with the next write
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Failed to send subscription: " + exc.getMessage());
            }
        });
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
                    System.out.println("Connection closed by broker. Attempting to reconnect...");
                    closeChannel(); // Close the channel
                    reconnect();    // Trigger the reconnect logic
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Failed to read data: " + exc.getMessage() + ". Attempting to reconnect...");
                closeChannel();
                reconnect();
            }
        });
    }

    private void reconnect() {
        while (reconnectAttempts < 5) {
            try {
                socketChannel = AsynchronousSocketChannel.open(); // Re-open the socket channel
                connect("localhost", 8080); // Reconnect to the broker
                startListening();           // Restart listening for messages
                return; // Exit if connection is successful
            } catch (InterruptedException | IOException e) {
                System.err.println("Failed to reconnect: " + e.getMessage());
                reconnectAttempts++;
                try {
                    Thread.sleep(2000); // Wait before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        askForReconnect(); // Ask the user if they want to reconnect after 5 attempts
    }

    private void askForReconnect() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Connection lost. Would you like to reconnect? (yes/no)");
        String response = scanner.nextLine().trim().toLowerCase();

        if ("yes".equals(response)) {
            reconnectAttempts = 0; // Reset attempts for the new attempt
            reconnect(); // Attempt to reconnect again
        } else {
            System.out.println("Subscriber will not reconnect.");
        }
    }

    private void closeChannel() {
        try {
            if (socketChannel != null && socketChannel.isOpen()) {
                socketChannel.close();
            }
        } catch (IOException e) {
            System.err.println("Failed to close socket: " + e.getMessage());
        }
    }
}
