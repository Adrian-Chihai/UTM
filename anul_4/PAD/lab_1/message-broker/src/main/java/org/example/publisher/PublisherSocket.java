package org.example.publisher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class PublisherSocket {
    private final AsynchronousSocketChannel publisherSocket;
    public boolean isConnected;

    public PublisherSocket() throws IOException {
        publisherSocket = AsynchronousSocketChannel.open();
    }

    public AsynchronousSocketChannel getPublisherSocket() {
        return publisherSocket;
    }

    public void connect(String ip, int port) throws InterruptedException {
        InetSocketAddress hostAddress = new InetSocketAddress(ip, port);
        publisherSocket.connect(hostAddress, null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                System.out.println("Publisher connected to broker.");
                isConnected = true;
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("Publisher could not connect failed to broker: " + exc.getMessage());
                isConnected = false;
            }
        });
        Thread.sleep(2000); // Wait for the connection to be established
    }

    public void send(byte[] data) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            ByteBuffer buffer = ByteBuffer.allocate(data.length); // Create a new buffer
            buffer.put(data); // Put data into the buffer
            buffer.flip(); // Prepare the buffer for writing
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
                }
            });
        } catch (Exception e) {
            System.out.println("Data could not be sent. " + e.getMessage());
            latch.countDown();
        }
        latch.await(); // Wait for the send operation to complete
    }
}
