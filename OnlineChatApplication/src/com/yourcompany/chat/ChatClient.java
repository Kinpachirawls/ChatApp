package com.yourcompany.chat;

import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_IP = "localhost"; // Change this to the server's IP address if it's on a different machine
    private static final int SERVER_PORT = 12345; // Make sure this port matches the server's port

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to server.");

            // Start a thread to continuously receive messages from the server
            Thread receiveThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                        System.out.print("> "); // Display prompt for user input
                    }
                } catch (IOException e) {
                    System.err.println("Error receiving message from server: " + e.getMessage());
                }
            });
            receiveThread.start();

            // Read user input from the console and send it to the server
            String userInput;
            System.out.print("> "); // Initial prompt for user input
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.print("> "); // Display prompt for next user input
            }
        } catch (IOException e) {
            System.err.println("Error in client: " + e.getMessage());
        }
    }
}
