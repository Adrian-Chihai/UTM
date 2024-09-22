package org.example.broker;

import org.example.common.ConnectionInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.ByteBuffer;

public class BrokerSocket {
    private AsynchronousServerSocketChannel serverSocket;

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

                // Create and populate ConnectionInfo object
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

        // Declare and initialize the readHandler
        final CompletionHandler<Integer, ByteBuffer> readHandler = new CompletionHandler<>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if (result == -1) {
                    System.out.println("maybe is null");
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

                connectionInfo.setData(data);

                // Write the received data back to the client
                clientChannel.write(ByteBuffer.wrap(data), buffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        System.out.println("Data sent back to client");
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println("Failed to send data: " + exc.getMessage());
                    }
                });

                buffer.clear(); // Clear the buffer for the next read
                clientChannel.read(buffer, buffer, this);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer buffer) {
                System.out.println("Failed to read data: " + exc.getMessage());
            }
        };

        clientChannel.read(buffer, buffer, readHandler);
    }
}
