package org.example.lab2;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static DatagramSocket datagramSocket;
    private static InetAddress serverAddress;

    static {
        try {
            datagramSocket = new DatagramSocket();
            serverAddress = InetAddress.getByName("localhost");
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private String nickname;

    public static void main(String[] args) {
        System.out.print("Enter your nickname: ");
        Scanner scanner = new Scanner(System.in);
        String nickname = scanner.nextLine();

        Client client = new Client(nickname);
        client.init();

        Thread clientThread = new Thread(new ClientThread(datagramSocket));
        clientThread.start();

        client.startChat(scanner);
    }

    public Client(String nickname) {
        this.nickname = nickname;
    }

    private void init() {
        String initMessage = "init " + nickname;
        byte[] initData = initMessage.getBytes();

        try {
            DatagramPacket initPacket = new DatagramPacket(initData, initData.length, serverAddress, 8080);
            datagramSocket.send(initPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startChat(Scanner scanner) {
        System.out.println("Chat started. Type 'exit' to quit.");

        while (true) {
            System.out.print("[" + nickname + "]: ");
            String message = scanner.nextLine();

            if ("exit".equalsIgnoreCase(message)) {
                System.out.println("Exiting chat. Goodbye!");
                System.exit(0);
            }

            sendMessage(message);
        }
    }

    private void sendMessage(String message) {
        byte[] messageData = message.getBytes();

        try {
            DatagramPacket messagePacket = new DatagramPacket(messageData, messageData.length, serverAddress, 8080);
            datagramSocket.send(messagePacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
