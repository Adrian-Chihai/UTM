package org.example.lab2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ClientThread implements Runnable {
    private byte[] incoming = new byte[256];
    private DatagramSocket datagramSocket;

    public ClientThread(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run() {
        System.out.println("Starting Thread");

        while (true) {
            DatagramPacket datagramPacket = new DatagramPacket(incoming, incoming.length);

            try {
                datagramSocket.receive(datagramPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String receivedMessage = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), StandardCharsets.UTF_8);
            System.out.println("Received: " + receivedMessage);
        }
    }
}
