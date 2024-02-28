package org.example.lab2;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 8080;
    private static byte[] incoming = new byte[256];
    private static DatagramSocket datagramSocket;
    private static List<InetAddress> clientAddresses = new ArrayList<>();
    private static List<Integer> clientPorts = new ArrayList<>();

    static {
        try {
            datagramSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Started server on port " + PORT);

        while (true) {
            DatagramPacket packet = new DatagramPacket(incoming, incoming.length);

            try {
                datagramSocket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Server received: " + message);

            if (message.startsWith("init")) {
                handleInitMessage(packet);
            } else {
                forwardMessageToClients(packet);
            }
        }
    }

    private static void handleInitMessage(DatagramPacket packet) {
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();

        if (!clientAddresses.contains(clientAddress) && !clientPorts.contains(clientPort)) {
            clientAddresses.add(clientAddress);
            clientPorts.add(clientPort);
            System.out.println("New client joined: " + clientAddress.getHostAddress() + ":" + clientPort);
        }
    }

    private static void forwardMessageToClients(DatagramPacket packet) {
        byte[] byteMessage = packet.getData();

        for (int i = 0; i < clientAddresses.size(); i++) {
            InetAddress clientAddress = clientAddresses.get(i);
            int clientPort = clientPorts.get(i);

            DatagramPacket forward = new DatagramPacket(byteMessage, byteMessage.length, clientAddress, clientPort);

            try {
                datagramSocket.send(forward);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
