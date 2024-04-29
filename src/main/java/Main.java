import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// This class is the entry point of the program

public class Main {

    public static void main(String[] args){

        // Create a server socket
        ServerSocket serverSocket;
        // Port number
        int port = 6379;

        // Try to create a server socket and wait for a connection from a clients on the specified port

        try {

            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting reuse address to true
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            // Listen for incoming client connections indefinitely

            while (true) {
                // Wait for connection from a client
                Socket clientSocket = serverSocket.accept();
                // Create a new thread to handle the client connection
                Thread clientThread = new ClientHandler(clientSocket);
                // Start the thread
                clientThread.start();
            }

        }

        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

    }

}