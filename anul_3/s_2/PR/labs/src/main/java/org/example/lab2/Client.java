package org.example.lab2;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client implements Runnable {
    private Scanner input;
    private String nickname;
    private DatagramSocket clientSocket;
    private InetAddress inetAddress;
    private int port;

    public Client(int port) throws SocketException, UnknownHostException {
        input = new Scanner(System.in);
        clientSocket = new DatagramSocket();
        inetAddress = InetAddress.getByName("localhost");
        this.port = port;
    }

    @Override
    public void run() {

    }

    private void enterNickname() {
        System.out.println("Enter your nickname: ");
        nickname = input.nextLine();
    }

    public static void main(String[] args) {

    }
}
