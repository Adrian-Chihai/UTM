package org.example.lab1;

public class MessageValidator {

    public static boolean isValidMessage(String message) {
        return !message.isBlank();
    }
    public static boolean isValidChangeNickCommand(String message) {
        return message.startsWith("/nick");
    }

    public static boolean isValidQuitCommand(String message) {
        return message.startsWith("/quit");
    }
}
