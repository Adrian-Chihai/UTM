package org.example.lab2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    private static final int PORT = 9876;
    private static final List<ClientInfo> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            Scanner input = new Scanner(System.in);
            System.out.println("Server is running on port " + PORT);

            Thread receiveThread = new Thread(() -> {
                while (true) {
                    try {
                        byte[] receiveData = new byte[1024];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        serverSocket.receive(receivePacket);

                        String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        handleMessage(message, receivePacket.getAddress(), receivePacket.getPort());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            receiveThread.start();

            Thread consoleThread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String consoleMessage = scanner.nextLine();
                    handleConsoleMessage(consoleMessage);
                }
            });
            consoleThread.start();        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleMessage(String message, java.net.InetAddress address, int port) {
        System.out.println("Received: " + message + " from " + address + ":" + port);

        if (message.startsWith("/join")) {
            String username = message.substring(6);
            ClientInfo newClient = new ClientInfo(username, address, port);
            clients.add(newClient);
            sendToAllExcept("User " + username + " has joined the chat.", clients.getLast());
        } else if (message.equals("/quit")) {
            ClientInfo leavingClient = findClient(address, port);
            if (leavingClient != null) {
                clients.remove(leavingClient);
                sendToAll("User " + leavingClient.getUsername() + " has left the chat.");
            }
        } else if (message.startsWith("/tell")) {
            String[] parts = message.split(" ", 3);
            if (parts.length == 3) {
                String targetUsername = parts[1];
                String privateMessage = parts[2];
                sendToUser(privateMessage, findClientByUsername(targetUsername), address, port);
            }
        } else {
            ClientInfo sender = findClient(address, port);
            if (sender != null) {
                String formattedMessage = sender.getUsername() + ": " + message;
                sendToAllExcept(formattedMessage, sender);
            }
        }
    }

    private static void handleConsoleMessage(String message) {
        if (message.startsWith("/tell")) {
            String[] parts = message.split(" ", 3);
            if (parts.length == 3) {
                String targetUsername = parts[1];
                String privateMessage = parts[2];
                sendToUser(privateMessage, findClientByUsername(targetUsername));
            }
        } else {
            sendToAll("Server: " + message);
        }
    }

    private static void sendToAll(String message) {
        for (ClientInfo client : clients) {
            send(message, client.getAddress(), client.getPort());
        }
    }

    private static void sendToAllExcept(String message, ClientInfo sender) {
        for (ClientInfo client : clients) {
            if (!client.equals(sender)) {
                send(message, client.getAddress(), client.getPort());
            }
        }
    }

    private static void sendToAllFromServer(String message) {
        for (ClientInfo client : clients) {
            send(message, client.getAddress(), client.getPort());
        }
    }

    private static void sendToUser(String message, ClientInfo targetClient, java.net.InetAddress sourceAddress, int sourcePort) {
        if (targetClient != null) {
            String formattedMessage = "Private message from " + findClient(sourceAddress, sourcePort).getUsername() + ": " + message;
            send(formattedMessage, targetClient.getAddress(), targetClient.getPort());
        }
    }

    private static void sendToUser(String message, ClientInfo targetClient) {
        if (targetClient != null) {
            send("Private message from server: " + message, targetClient.getAddress(), targetClient.getPort());
        }
    }

    private static void send(String message, java.net.InetAddress address, int port) {
        try {
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, port);
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ClientInfo findClient(java.net.InetAddress address, int port) {
        for (ClientInfo client : clients) {
            if (client.getAddress().equals(address) && client.getPort() == port) {
                return client;
            }
        }
        return null;
    }

    private static ClientInfo findClientByUsername(String username) {
        for (ClientInfo client : clients) {
            if (client.getUsername().equals(username)) {
                return client;
            }
        }
        return null;
    }

    private static class ClientInfo {
        private final String username;
        private final java.net.InetAddress address;
        private final int port;

        public ClientInfo(String username, java.net.InetAddress address, int port) {
            this.username = username;
            this.address = address;
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public java.net.InetAddress getAddress() {
            return address;
        }

        public int getPort() {
            return port;
        }
    }
}
