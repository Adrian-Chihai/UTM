package org.example.publisher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class PublisherSocket {
    private AsynchronousSocketChannel publisherSocket;
    public boolean isConnected;

    public PublisherSocket() throws IOException {
        publisherSocket = AsynchronousSocketChannel.open();
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

        publisherSocket.connect(hostAddress, null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                System.out.println("Publisher connected to broker.");
                isConnected = true;
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("Publisher could not connect to broker: " + exc.getMessage());
                isConnected = false;
            }
        });

        Thread.sleep(2000); // Wait for the connection to be established
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
            System.out.println("Cannot send data. Publisher is not connected.");
            return; // Exit if not connected
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
                    latch.countDown();
                    isConnected = false; // Mark as disconnected on failure
                }
            });
        } catch (Exception e) {
            System.out.println("Data could not be sent. " + e.getMessage());
            latch.countDown();
        }
        latch.await();
    }
}
