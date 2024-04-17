package org.example.lab2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static final int PORT = 9876;
    private static List<String> commands = new ArrayList<>(List.of("/quit", "/tell"));

    public static void main(String[] args) throws SocketException {
        DatagramSocket socket = new DatagramSocket();
        Scanner input = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = input.nextLine();
        send("/join " + username, socket);

        Thread receiverThread = new Thread(() -> {
            while (true) {
                receive(socket);
            }
        });
        receiverThread.start();

        while (true) {

            String message = input.nextLine();
//            if (isCommand(message)) {
//                message = handleCommand(message, input);
//            }

            if (commands.get(0).equals(message)) {
                send("/quit", socket);
                socket.close();
                System.exit(0);
            } else if (message.startsWith(commands.get(1))) {
                send(message, socket);
            } else {
                send(message, socket);
            }
        }
    }

    private static void send(String message, DatagramSocket socket) {
        try {
            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), java.net.InetAddress.getByName("localhost"), PORT);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void receive(DatagramSocket socket) {
        try {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(receivedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String handleCommand(String message, Scanner input) {
        do {
            System.out.println(message + " is an invalid command");
            System.out.println("Please reenter");
            message = input.nextLine();
        } while (isValidCommand(message));
        return message;
    }

    private static boolean isValidCommand(String message) {
        String[] commandParts = message.split(" ");
        StringBuilder command = new StringBuilder();
        command.append(commandParts[0]);
        return commands.stream().anyMatch(e -> e.equals(command));
    }

    private static boolean isCommand(String message) {
        return message.startsWith("/");
    }
}
