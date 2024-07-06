# Dana Hall 
# Online Chat Application

This is a simple online chat application implemented in Java. It allows multiple users to connect to a central server, send messages, and receive messages from other users.

## How to Run

1. Start the ChatServer by running the `ChatServer` class.
2. Run multiple instances of the ChatClient by executing the `ChatClient` class. Each instance represents a connected user.
3. Use the text-based interface of the ChatClient to send and receive messages.

## Implementation Details

- The server listens for incoming connections on port 12345.
- Each client connection is handled by a separate thread on the server.
- Messages sent by clients are broadcasted to all connected clients.

## Dependencies

- This project does not have any external dependencies.

## Screenshots will be attached




