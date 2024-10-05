package org.example.broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.ConnectionInfo;
import org.example.common.Payload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BrokerSocket {
    private AsynchronousServerSocketChannel serverSocket;
    private final Map<String, List<AsynchronousSocketChannel>> subscribers = new ConcurrentHashMap<>();

    public BrokerSocket() throws IOException {
        serverSocket = AsynchronousServerSocketChannel.open();
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
                    try {
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
            String topic = message.split("#")[1];
            subscribeClientToTopic(connectionInfo.getSocket(), topic);
        } else {
            forwardMessageToSubscribers(data);
        }
    }

    private void subscribeClientToTopic(AsynchronousSocketChannel client, String topic) {
        subscribers.computeIfAbsent(topic, k -> new ArrayList<>()).add(client);
        System.out.println("Client subscribed to topic: " + topic);
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
}
