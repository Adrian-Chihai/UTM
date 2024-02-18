package org.example.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private Thread clientThread;
    private boolean done;

    @Override
    public void run() {
        try {
            client = new Socket("localhost", 9999);
            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inputHandler = new InputHandler();
            clientThread = new Thread(inputHandler);
            clientThread.start();

            String inputMessage;

            while ((inputMessage = input.readLine()) != null) {
                System.out.println(inputMessage);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        done = true;
        try {
            input.close();
            output.close();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {

        }
    }

    class InputHandler implements Runnable {
        @Override
        public void run() {
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inputReader.readLine();
                    if (message.equals("/quit")) {
                        output.println(message);
                        inputReader.close();
                        shutdown();
                    } else {
                        output.println(message);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "client=" + client +
                '}';
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
