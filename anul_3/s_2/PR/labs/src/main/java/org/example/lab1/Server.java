package org.example.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private final int port = 9999;
    private List<ConnectionHandler> connectionList;
    private ServerSocket server;
    private ExecutorService pool;
    private boolean done;

    public Server() {
        connectionList = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            pool = Executors.newCachedThreadPool();

            while (!done) {
                Socket client = server.accept();
                ConnectionHandler connectionHandler = new ConnectionHandler(client);
                connectionList.add(connectionHandler);
                pool.execute(connectionHandler);
            }
        } catch (Exception e) {
            shutdown();
        }
    }

    public void broadcast(ConnectionHandler sender, String message) {
        for (ConnectionHandler ch : connectionList) {
            if (ch != null && !ch.equals(sender)) {
                ch.sendMessage(message);
            }
        }
    }

    public void shutdown() {
        try {
            done = true;
            pool.shutdown();

            if (!server.isClosed()) {
                server.close();
            }

            for (ConnectionHandler connectionHandler : connectionList) {
                connectionHandler.shutdown();
            }
        } catch (IOException e) {

        }
    }




    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader input;
        private PrintWriter output;
        private String nickname;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {

            try {
                output = new PrintWriter(client.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(client.getInputStream()));

                connectingProcess();

                String message;
                while (MessageValidator.isValidMessage(message = enterMessage(input))) {
                     if (MessageValidator.isValidChangeNickCommand(message)) {
                         String[] messageSplit = message.split(" ", 2);
                         if (messageSplit.length == 2) {
                             displayWhenNicknameChanged(extractNickname(messageSplit));
                         } else {
                             displayWhenNoNicknameProvided();
                         }
                     } else if (MessageValidator.isValidQuitCommand(message)) {
                         displayWhenUserLeaves();
                     } else {
                         displayWhenSendMessage(message);
                     }
                }
            } catch (IOException e) {
                shutdown();
            }
        }

        public void shutdown() {
            try {
                input.close();
                output.close();
                client.close();
            } catch (IOException e) {

            }
        }

        public void sendMessage(String message) {
            output.println(message);
        }

        private void connectingProcess() throws IOException {
            do {
                clientEnterNickname();

                if (nickname.isBlank()) {
                    displayWhenConnectionFailed();
                }

            } while (ClientValidator.isValidNickname(nickname));

            displayWhenConnectionEstablished();
        }

        private void clientEnterNickname() throws IOException {
            output.println("Enter your nickname: ");
            nickname = input.readLine();
        }

        private void displayWhenConnectionEstablished() {
            printServerLogs(nickname + " with address " + client.getInetAddress() + ":" + client.getPort() + " connected to the server.");
            broadcast(this, nickname + " joined the chat.");
        }

        private void displayWhenConnectionFailed() {
            printServerLogs(client.getInetAddress() + ":" + client.getPort() + " entered an invalid nickname.");
            output.println("Invalid nickname, you need to reenter");
        }

        private void displayWhenSendMessage(String message) {
            printServerLogs(nickname + " with address " + client.getInetAddress() + ":" + client.getPort() + " sent a message -> " + message);
            broadcast(this, nickname + ": " + message);
        }

        private String enterMessage(BufferedReader input) throws IOException {
            return input.readLine();
        }

        private void displayWhenNicknameChanged(String nicknameChanged) {
            broadcast(this, nickname + " changed nickname " + nicknameChanged);
            printServerLogs(nickname + " changed nickname " + nicknameChanged);
            nickname = nicknameChanged;
            output.println("Successfully changed nickname: " + nickname);
        }

        private void displayWhenNoNicknameProvided() {
            output.println("No nickname provided");
        }

        private void displayWhenUserLeaves() {
            printServerLogs(nickname + "(" + client.getInetAddress() + ":" + client.getPort() + ")" + " left.");
            broadcast(this, nickname + " left the chat.");
            shutdown();
        }

        private String extractNickname(String[] messageSplit) {
            return messageSplit[1];
        }

        private void printServerLogs(String log) {
            System.out.println(log);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
