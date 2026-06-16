package RozbudowaneStrukturyJava.Serwer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ChatServer {
    public final static int PORT = 12345;
    public Map<String, ClientHandler> usersMap = new ConcurrentHashMap<>();


    public void broadcast(String message, String user) {
        for (String string : usersMap.keySet()) {
            if(string.equals(user)) continue;
            usersMap.get(string).sendMessage(message);

        }
    }
    public static void main(String[] args) {
        System.out.println(String.format("Server started on port %d", PORT));
        ChatServer chatServer = new ChatServer();

        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket,chatServer)).start();

            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
}