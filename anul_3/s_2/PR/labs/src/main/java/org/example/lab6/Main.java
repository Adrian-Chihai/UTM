package org.example.lab6;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceți zona geografică (de exemplu, 'GMT+3' sau 'GMT-5'):");
        String userInput = scanner.nextLine();

        try {
            String formattedZone = "GMT" + (userInput.startsWith("GMT") ? userInput.substring(3) : userInput);
            ZoneId zoneId = ZoneId.of(formattedZone);
            ZonedDateTime dateTime = ZonedDateTime.now(zoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            System.out.println("Ora exactă pentru zona " + userInput + " este: " + dateTime.format(formatter));
        } catch (Exception e) {
            System.out.println("A fost o eroare la procesarea zonei geografice. Asigurați-vă că formatul este corect.");
        }
    }

}
