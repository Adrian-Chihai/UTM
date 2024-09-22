package org.example.broker;

import java.io.IOException;

public class Broker {
    public static void main(String[] args) throws IOException, InterruptedException {
        while (true) {
            BrokerSocket broker = new BrokerSocket();
            broker.start("localhost", 8080);

            synchronized (broker) {
                broker.wait();
            }
        }
    }
}
