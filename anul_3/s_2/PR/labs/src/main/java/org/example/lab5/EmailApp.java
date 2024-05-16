package org.example.lab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class EmailApp {
    private static Client emailClient;

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String emailAddress = "";
            String password = "";
            emailClient = new Client(emailAddress, password);
            emailClient.login();
            System.out.println("\nLogin successful!");

            while (true) {
                System.out.println("\nOPTIONS: \n1. Send Email \n2. Send Email with Attachment \n3. List Inbox IMAP\n4. List Inbox POP3\n5. Quit");
                System.out.print("Choose an option: ");
                String option = reader.readLine();

                switch (option) {
                    case "1":
                        sendEmail(reader, false);
                        break;
                    case "2":
                        sendEmail(reader, true);
                        break;
                    case "3":
                        listInboxImap(reader);
                        break;
                    case "4":
                        listInboxPop(reader);
                    case "5":
                        emailClient.logout();
                        return;
                    default:
                        System.out.println("Invalid option, please try again.");
                        break;
                }
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

    private static void sendEmail(BufferedReader reader, boolean withAttachment) throws IOException, MessagingException {
        System.out.print("Enter recipient's email: ");
        String toAddress = reader.readLine();
        System.out.print("Enter subject: ");
        String subject = reader.readLine();
        System.out.print("Enter body: ");
        String body = reader.readLine();

        if (withAttachment) {
            System.out.print("Enter attachment path: ");
            String attachmentPath = reader.readLine();
            emailClient.sendEmailWithAttachment(toAddress, subject, body, attachmentPath, null);
            System.out.println("Email with attachment sent successfully.");
        } else {
            emailClient.sendTextEmail(toAddress, subject, body, null);
            System.out.println("Email sent successfully.");
        }
    }

    private static void listInboxImap(BufferedReader reader) throws IOException, MessagingException {
        Message[] messages = emailClient.getEmails("IMAP");
        int index = 0;

        while (index < messages.length) {
            System.out.println("Inbox Emails:");

            for (int i = 0; i < 10 && index < messages.length; i++, index++) {
                Message message = messages[index];
                System.out.println("ID: " + index + " - From: " + Arrays.toString(message.getFrom()) + " - Subject: " + message.getSubject());
            }

            System.out.print("Press 'n' to view next 10 emails, 'd' to download an email, or 'q' to quit: ");
            String choice = reader.readLine();
            if (choice.equalsIgnoreCase("d")) {
                downloadEmail(reader, messages);
            } else if (choice.equalsIgnoreCase("q")) {
                System.out.println("Exiting...");
                break;
            } else if (!choice.equalsIgnoreCase("n")) {
                System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void listInboxPop(BufferedReader reader) throws IOException, MessagingException {
        Message[] messages = emailClient.getEmails("POP3");
        int index = 0;

        while (index < messages.length) {
            System.out.println("Inbox Emails:");

            for (int i = 0; i < 10 && index < messages.length; i++, index++) {
                Message message = messages[index];
                System.out.println("ID: " + index + " - From: " + Arrays.toString(message.getFrom()) + " - Subject: " + message.getSubject());
            }

            System.out.print("Press 'n' to view next 10 emails, 'd' to download an email, or 'q' to quit: ");
            String choice = reader.readLine();
            if (choice.equalsIgnoreCase("d")) {
                downloadEmail(reader, messages);
            } else if (choice.equalsIgnoreCase("q")) {
                System.out.println("Exiting...");
                break;
            } else if (!choice.equalsIgnoreCase("n")) {
                System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void downloadEmail(BufferedReader reader, Message[] messages) throws IOException, MessagingException {
        System.out.print("Enter the ID of the email you want to download: ");
        int id = Integer.parseInt(reader.readLine());
        Message message = messages[id];

        Object content = message.getContent();
        if (content instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) content;

            for (int i = 0; i < multipart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    System.out.println("Downloading " + part.getFileName());
                    part.saveFile("C:/Users/Adrian/Desktop/" + part.getFileName());
                    System.out.println("Download successful for " + part.getFileName());
                }
            }
            System.out.println("Download complete for email ID: " + id);
        } else {
            System.out.println("The email does not contain multipart content to download.");
        }
    }

}
