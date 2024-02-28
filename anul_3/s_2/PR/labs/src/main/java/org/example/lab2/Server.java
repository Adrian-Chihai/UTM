package org.example.lab2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server implements Runnable {
    private int serverPort;
    private DatagramSocket serverSocket;
    private byte[] receivedData;
    private DatagramPacket receivedPacket;
    private boolean alive;

    public Server(int serverPort) throws SocketException {
        this.serverPort = serverPort;
        serverSocket = new DatagramSocket(serverPort);
        receivedData = new byte[1024];
    }

    @Override
    public void run() {
        while (!alive) {
            receivedPacket = new DatagramPacket(receivedData, receivedData.length);
            try {
                serverSocket.receive(receivedPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            InetAddress inetAddress = receivedPacket.getAddress();
            int port = receivedPacket.getPort();
            receivedPacket = new DatagramPacket(receivedData, receivedData.length, port);
            String clientMessage = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println(inetAddress + ": " + clientMessage);

            try {
                serverSocket.send(receivedPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws SocketException {
        Server server = new Server(8080);
        server.run();
    }
}
