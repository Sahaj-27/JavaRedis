import RedisProtocol.*;
import Requests.*;
import java.io.*;
import java.net.Socket;

class ClientHandler extends Thread {

    // Create a client socket
    private final Socket clientSocket;

    // Create input and output streams
    private InputStream inputStream;
    private OutputStream outputStream;

    // Set the client socket
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    // Run the client handler thread
    @Override

    public void run() {

        try {

            // Get the input and output streams
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();

            // Create a request parser and a request processor
            RequestParser requestParser = new RequestParser(inputStream);
            RequestProcessor requestProcessor = new RequestProcessor();

            // Handle multiple requests from the client
            while (!clientSocket.isClosed()) {
                // Parse the request and process it
                RedisData request = requestParser.parseRequest();
                System.out.println("Request: " + request);
                RedisData response = requestProcessor.processRequest(request);
                System.out.println("Response: " + response);
                // Write the response to the output stream
                outputStream.write(response.getFormattedValue().getBytes());
                // Flush the output stream
                outputStream.flush();
            }

        }

        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }

    }

}