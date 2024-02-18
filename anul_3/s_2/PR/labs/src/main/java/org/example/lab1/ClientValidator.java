package org.example.lab1;

public class ClientValidator {
    public static boolean isValidNickname(String nickname) {
        return !nickname.isBlank() && nickname.contains(" ");
    }
}
