package org.example.subscriber;

import java.io.IOException;
import java.util.Scanner;

public class Subscriber {
    public static void main(String[] args) {
        System.out.println("Subscriber started");

        // Define broker IP and port (you can modify these as needed)
        String brokerIp = "localhost"; // Change this to the broker's IP
        int brokerPort = 8080; // Change this to the broker's port

        // Create a single SubscriberSocket instance
        SubscriberSocket subscriberSocket;
        try {
            subscriberSocket = new SubscriberSocket(); // Initialize the SubscriberSocket
            subscriberSocket.connect(brokerIp, brokerPort); // Connect to the broker
            subscriberSocket.startListening(); // Start listening for messages

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Enter topic to subscribe (or type 'exit' to quit): ");
                String topic = scanner.nextLine().toLowerCase();

                if ("exit".equals(topic)) {
                    System.out.println("Exiting subscriber...");
                    break;
                }
                subscriberSocket.subscribe(topic);
            }

            scanner.close(); // Close the scanner before exiting
        } catch (IOException e) {
            System.err.println("Error creating SubscriberSocket: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Connection interrupted: " + e.getMessage());
        }
    }
}
