package org.example.lab3;

import org.apache.commons.validator.routines.InetAddressValidator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static String dnsServer = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">");
            String input = scanner.nextLine();
            String[] tokens = input.split("\\s+");

            if (tokens.length < 2) {
                System.out.println("Comandă invalidă. Folosește \"resolve <domain>\" sau \"resolve <ip>\" sau \"use dns <ip>\".");
                continue;
            }

            String command = tokens[0];
            String argument = tokens[1];

            switch (command) {
                case "resolve":
                    if (tokens.length != 2) {
                        System.out.println("Comandă invalidă. Folosește \"resolve <domain>\" sau \"resolve <ip>\".");
                        break;
                    }
                    try {
                        InetAddress[] addresses;
                        if (isIPAddress(argument)) {
                            addresses = InetAddress.getAllByName(argument);
                            System.out.println("Domenii asociate IP-ului " + argument + ":");
                            for (InetAddress addr : addresses) {
                                System.out.println(addr.getCanonicalHostName());
                            }
                        } else {
                            addresses = InetAddress.getAllByName(argument);
                            System.out.println("IP-uri asociate domeniului " + argument + ":");
                            for (InetAddress addr : addresses) {
                                System.out.println(addr.getHostAddress());
                            }
                        }
                    } catch (UnknownHostException e) {
                        System.out.println("DNS nu a putut rezolva " + argument);
                    }
                    break;
                case "use":
                    if (tokens.length != 3 || !tokens[1].equals("dns")) {
                        System.out.println("Comandă invalidă. Folosește \"use dns <ip>\".");
                        break;
                    }
                    String newDnsServer = tokens[2];
                    if (!isValidIPAddress(newDnsServer)) {
                        System.out.println("Adresa DNS serverului este invalidă.");
                        break;
                    }
                    dnsServer = newDnsServer;
                    System.out.println("DNS server setat la: " + dnsServer);
                    break;
                default:
                    System.out.println("Comandă necunoscută.");
            }
        }
    }


    private static boolean isIPAddress(String str) {
        return new InetAddressValidator().isValid(str);
    }

    private static boolean isValidIPAddress(String str) {
        return isIPAddress(str) || str.equals("localhost");
    }
}
