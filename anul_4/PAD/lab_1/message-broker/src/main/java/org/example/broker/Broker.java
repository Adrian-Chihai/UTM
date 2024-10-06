package org.example.broker;

import java.io.IOException;

public class Broker {
    public static void main(String[] args) throws IOException {
        BrokerSocket broker = new BrokerSocket();
        broker.start("localhost", 8080); // Start the broker
        // Keep the broker running indefinitely
        synchronized (broker) {
            while (true) {
                try {
                    broker.wait(); // Prevents main thread from exiting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Broker interrupted");
                    break;
                }
            }
        }
    }
}
