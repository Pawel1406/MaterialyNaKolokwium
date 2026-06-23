package RozbudowaneStrukturyJava.SerwerGUi;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    private static final int PORT = 12345;

    private static Map<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Serwer uruchomiony na porcie " + PORT);
        ChatServer.listen();
    }
    public static void listen(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private String username;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/login ")) {
                        this.username = message.substring(7).trim();
                        clients.put(this.username, this);
                        broadcast("/login " + this.username);
                        sendOnlineList();

                    } else if (message.startsWith("/broadcast ")) {
                        String content = message.substring(11);
                        broadcast("/msg " + this.username + ": " + content);

                    } else if (message.startsWith("/get_online")) {
                        sendOnlineList();
                    }
                }
            } catch (IOException e) {
                System.err.println("Błąd połączenia");
                e.printStackTrace();
            }
            finally {
                if (username != null) {
                    clients.remove(username);
                    broadcast("/logout " + username);
                }
            }
        }

        private void broadcast(String msg) {
            for (ClientHandler writer : clients.values()) {
                writer.out.println(msg);
            }
        }

        private void sendOnlineList() {
            String list = String.join(", ", clients.keySet());
            out.println("/online " + list);
        }
    }
}
