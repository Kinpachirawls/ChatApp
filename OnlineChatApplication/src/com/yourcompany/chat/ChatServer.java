package com.yourcompany.chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345; // Port number for server to listen on
    private static Set<PrintWriter> clientWriters = new HashSet<>(); // Set to store PrintWriter objects for connected clients

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { // Create a ServerSocket to listen for client connections
            System.out.println("Server is running...");
            while (true) { // Server runs indefinitely to accept multiple client connections
                new ClientHandler(serverSocket.accept()).start(); // Accept incoming client connections and start a new thread to handle each client
            }
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage()); // Print error message if server encounters an IO exception
        }
    }

    // Inner class to handle communication with individual clients
    private static class ClientHandler extends Thread {
        private Socket clientSocket; // Socket for communication with client
        private PrintWriter out; // PrintWriter to send messages to client

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true); // Create PrintWriter for this client
                clientWriters.add(out); // Add PrintWriter to set of client writers
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Create BufferedReader to read messages from client

                String inputLine;
                while ((inputLine = in.readLine()) != null) { // Read messages from client until connection is closed
                    for (PrintWriter writer : clientWriters) { // Broadcast message to all connected clients
                        writer.println(inputLine);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage()); // Print error message if exception occurs while handling client
            } finally {
                if (out != null) {
                    clientWriters.remove(out); // Remove PrintWriter from set when client disconnects
                }
                try {
                    clientSocket.close(); // Close client socket
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " + e.getMessage()); // Print error message if exception occurs while closing client socket
                }
            }
        }
    }
}
