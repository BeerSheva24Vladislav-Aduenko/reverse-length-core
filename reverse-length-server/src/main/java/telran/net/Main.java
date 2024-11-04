package telran.net;

import java.net.*;
import java.io.*;

public class Main {
    private static final int PORT = 3000;

    public static void main(String[] args) throws Exception {
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            runSession(socket);
        }
    }

    private static void runSession(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream writer = new PrintStream(socket.getOutputStream());) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String response = processRequest(line);
                writer.printf("Server response: %s\n", response);
            }
        } catch (Exception e) {
            System.out.println("client closed connection abnormally");
        }
    }

    private static String processRequest(String request) {
        return request.startsWith("reverse ") ? reverseRequest(request)
                : (request.startsWith("length ") ? lengthRequest(request) : "Wrong request");
    }

    private static String reverseRequest(String request) {
        String reverseRequest = request.substring("reverse ".length());
        return new StringBuilder(reverseRequest).reverse().toString();
    }

    private static String lengthRequest(String request) {
        String lengthRequest = request.substring("length ".length());
        return String.valueOf(lengthRequest.length());
    }
}