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

        if (publisherSocket.isConnected) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                Payload payload = new Payload();
                System.out.println("Enter topic:");
                payload.topic = scanner.nextLine().toLowerCase();

                System.out.println("Enter message:");
                payload.message = scanner.nextLine();

                String payloadString = mapper.writeValueAsString(payload);
                System.out.println("Sending: " + payloadString);
                byte[] payloadBytes = payloadString.getBytes(StandardCharsets.UTF_8);
                System.out.println("bytes - " + payloadBytes);



                publisherSocket.send(payloadBytes);
            }
        } else {
            System.out.println("Failed to connect to the broker.");
        }
    }
}
