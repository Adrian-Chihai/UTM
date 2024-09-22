package org.example.subscriber;

import java.io.IOException;
import java.util.Scanner;

public class Subscriber {
    public static void main(String[] args) {
        System.out.println("Subscriber started");

        // Define broker IP and port (you can modify these as needed)
        String brokerIp = "localhost"; // Change this to the broker's IP
        int brokerPort = 8080; // Change this to the broker's port

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Enter topic (or type 'exit' to quit): ");
                String topic = scanner.nextLine().toLowerCase();

                if ("exit".equals(topic)) {
                    System.out.println("Exiting subscriber...");
                    break; // Exit the loop if the user types "exit"
                }

                try {
                    SubscriberSocket subscriberSocket = new SubscriberSocket(topic);
                    subscriberSocket.connect(brokerIp, brokerPort);
                    subscriberSocket.startListening(); // Start listening for messages
                } catch (IOException e) {
                    System.err.println("Error creating SubscriberSocket: " + e.getMessage());
                } catch (InterruptedException e) {
                    System.err.println("Connection interrupted: " + e.getMessage());
                }
            }
        }
    }
}
