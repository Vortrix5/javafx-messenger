package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import chat.ClientHandler;

public class Server {
    private static final int PORT = 6655;
    public static final ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server]::: is listening on port " + PORT);
            while (true) {
                ClientHandler clientConnection = null;
                Socket socket = serverSocket.accept();
                Scanner scanner = new Scanner(System.in);

                clientConnection = new ClientHandler(socket);

                clients.add(clientConnection);
                clientConnection.start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + PORT);
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients)
                if (client != sender) {
                    client.sendMessage("[Client] " + message);
                }
        }
    }


}