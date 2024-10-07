package org.example.publisher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class PublisherSocket {
    private AsynchronousSocketChannel publisherSocket;
    public boolean isConnected;

    // Queue to store messages when the broker is down
    private final Queue<byte[]> messageQueue;

    public PublisherSocket() throws IOException {
        publisherSocket = AsynchronousSocketChannel.open();
        messageQueue = new ConcurrentLinkedQueue<>(); // Initialize the message queue
    }

    public void connect(String ip, int port) throws InterruptedException {
        // Close the existing socket if it's already connected
        if (isConnected) {
            close();
        }

        // Create a new socket and attempt to connect
        try {
            publisherSocket = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InetSocketAddress hostAddress = new InetSocketAddress(ip, port);
        CountDownLatch latch = new CountDownLatch(1);

        publisherSocket.connect(hostAddress, null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                System.out.println("Publisher connected to broker.");
                isConnected = true;

                // Once connected, attempt to send any messages stored in the queue
                resendQueuedMessages();
                latch.countDown();
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("Publisher could not connect to broker: " + exc.getMessage());
                isConnected = false;
                latch.countDown();
            }
        });

        latch.await(); // Wait for connection to complete
        Thread.sleep(2000); // Additional wait to ensure connection is fully established
    }

    public void close() {
        try {
            if (publisherSocket != null && publisherSocket.isOpen()) {
                publisherSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error while closing the socket: " + e.getMessage());
        }
        isConnected = false; // Mark as disconnected when closed
    }

    public void send(byte[] data) throws InterruptedException {
        if (!isConnected) {
            System.out.println("Broker is down. Adding message to queue.");
            messageQueue.add(data); // Add to the queue if not connected
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        try {
            ByteBuffer buffer = ByteBuffer.allocate(data.length);
            buffer.put(data);
            buffer.flip();

            publisherSocket.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    System.out.println("Data sent successfully!");
                    latch.countDown();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer buffer) {
                    System.out.println("Failed to send data: " + exc.getMessage());
                    messageQueue.add(data); // Add back to the queue if send failed
                    isConnected = false; // Mark as disconnected on failure
                    latch.countDown();
                }
            });
        } catch (Exception e) {
            System.out.println("Data could not be sent. " + e.getMessage());
            messageQueue.add(data); // Add to queue if there is any exception
            latch.countDown();
        }
        latch.await(); // Wait for the send operation to complete
    }

    // Method to resend messages in the queue when the broker comes back online
    private void resendQueuedMessages() {
        while (!messageQueue.isEmpty() && isConnected) {
            byte[] message = messageQueue.poll(); // Get and remove the next message from the queue
            if (message != null) {
                try {
                    send(message); // Attempt to send the message
                } catch (InterruptedException e) {
                    System.out.println("Failed to resend message: " + e.getMessage());
                }
            }
        }
    }
}
