package org.example.publisher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.Payload;

public class Publisher {
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Publisher");

        PublisherSocket publisherSocket = new PublisherSocket();
        publisherSocket.connect("localhost", 8080);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!publisherSocket.isConnected) {
                System.out.println("Lost connection to the broker. Would you like to reconnect? (y/n)");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("y")) {
                    System.out.println("Attempting to reconnect...");
                    publisherSocket.connect("localhost", 8080);
                } else {
                    System.out.println("Exiting Publisher.");
                    publisherSocket.close(); // Close the socket before exiting
                    break; // Exit the loop if the user doesn't want to reconnect
                }
            } else {
                Payload payload = new Payload();
                System.out.println("Enter topic:");
                payload.topic = scanner.nextLine().toLowerCase();

                System.out.println("Enter message:");
                payload.message = scanner.nextLine();

                String payloadString = mapper.writeValueAsString(payload);
                System.out.println("Sending: " + payloadString);
                byte[] payloadBytes = payloadString.getBytes(StandardCharsets.UTF_8);

                // Send the payload
                publisherSocket.send(payloadBytes);
            }
        }

        // Cleanup resources
        scanner.close();
        publisherSocket.close(); // Close the socket
        System.out.println("Publisher stopped.");
    }
}
