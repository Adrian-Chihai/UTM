package org.example.subscriber;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class SubscriberSocket {
    private final AsynchronousSocketChannel socketChannel;
    private boolean isConnected;
    private final String topic;

    public SubscriberSocket(String topic) throws IOException {
        this.topic = topic;
        socketChannel = AsynchronousSocketChannel.open();
    }

    public void connect(String ip, int port) throws InterruptedException {
        InetSocketAddress hostAddress = new InetSocketAddress(ip, port);
        CountDownLatch latch = new CountDownLatch(1);

        socketChannel.connect(hostAddress, null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                System.out.println("Subscriber connected to broker.");
                isConnected = true;
                sendSubscription(topic);
                latch.countDown();
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("Failed to connect to broker: " + exc.getMessage());
                isConnected = false;
                latch.countDown();
            }
        });

        latch.await();
    }

    public void startListening() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        readFromChannel(buffer);
    }

    private void sendSubscription(String topic) {
        String message = "subscribe#" + topic;
        send(message.getBytes(StandardCharsets.UTF_8));
    }

    private void send(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        socketChannel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("Subscription sent for topic: " + topic);
                startReceivingMessages(); // Start receiving messages after sending subscription
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Failed to send subscription: " + exc.getMessage());
            }
        });
    }

    private void startReceivingMessages() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        readFromChannel(buffer);
    }

    private void readFromChannel(ByteBuffer buffer) {
        socketChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (result > 0) {
                    attachment.flip(); // Prepare buffer for reading
                    byte[] data = new byte[attachment.remaining()];
                    attachment.get(data);
                    String message = new String(data, StandardCharsets.UTF_8);
                    System.out.println("Received message: " + message);

                    attachment.clear(); // Clear buffer for next read
                    readFromChannel(attachment); // Continue reading
                } else if (result == -1) {
                    System.out.println("Connection closed by broker.");
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        System.err.println("Failed to close socket: " + e.getMessage());
                    }
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Failed to read data: " + exc.getMessage());
                try {
                    socketChannel.close(); // Close channel on failure
                } catch (IOException e) {
                    System.err.println("Failed to close socket: " + e.getMessage());
                }
            }
        });
    }
}
