package Dimitruk.Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Server {
    private ServerSocket serverSocket;
    private HashMap<String,ClientHandler> handlers = new HashMap<>();

    public Server(int port) throws IOException {
       this.serverSocket = new ServerSocket(port);
    }

    public void listen() throws IOException {
        System.out.println("Server started");
        while(true) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket, this);
            Thread thread = new Thread(handler);
            thread.start();
            handlers.put(handler.getLogin(),handler);
        }
    }

    public void online(ClientHandler client) {
        client.send(
                handlers.values().stream()
                        .map(ClientHandler::getLogin).collect(Collectors.joining("\n"))
        );
    }
    public String signMessage(String message, ClientHandler sender) {
        return String.format("%s: %s", sender.getLogin(), message);
    }

    public void broadcast(String message, ClientHandler sender) {
        handlers.values().stream()
        //        .filter(receiver -> receiver != sender)
                .forEach(handler -> handler.send(signMessage(message, sender)));
    }

    public void whisper(String message, ClientHandler sender, String recipientLogin) {
        handlers.get(recipientLogin).send(signMessage(message, sender));
    }
}