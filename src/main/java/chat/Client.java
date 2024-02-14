package chat;

import chat.ClientListener;

import java.io.*;
import java.net.*;

public class Client {
    String hostName = "localhost";
    int portNumber = 6655;
    Socket socket = null;
    PrintWriter out;
    BufferedReader in;
    BufferedReader stdIn;
    private ClientListener listener;

    public Client(){
        try {
            System.out.println("Connected to server");
            socket = new Socket(hostName, portNumber);
        } catch (IOException ex) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
        }
    }

    public void start(ClientListener listener) {
        try {
            this.listener = listener;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            receivingMessages();
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + hostName);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
        }
    }

    public void receivingMessages() {
        Thread receiveMessagesThread = new Thread(() -> {
            try {
                String serverResponse;
                while ((serverResponse = in.readLine()) != null) {
                    listener.onMessageReceived(serverResponse);
                    System.out.println("Server: " + serverResponse);
                }
            } catch (IOException e) {
                System.err.println("Error receiving messages: " + e.getMessage());
            }
        });
        receiveMessagesThread.start();
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}