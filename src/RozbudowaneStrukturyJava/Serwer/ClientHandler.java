package RozbudowaneStrukturyJava.Serwer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket; //to jest polaczenie z klientem
    private PrintWriter out;
    private BufferedReader in;
    ChatServer chatServer;
    String login;

    public ClientHandler(Socket socket,ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            login = in.readLine();
            if (login == null || login.trim().isEmpty()) {
                return;
            }
            this.login = this.login.trim();
            chatServer.usersMap.put(this.login, this);
            chatServer.broadcast(">>>Dolaczyl user o loginie " + this.login + "<<<", this.login);

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                if (clientMessage.equals("/online")) {
                    this.sendMessage(chatServer.usersMap.keySet().toString());
                } else {
                    System.out.println("Otrzymano od użytkownika: "+login+" wiadomość: " + clientMessage);
                    chatServer.broadcast(this.login+": "+clientMessage, this.login);
                }
            }
        } catch (IOException e) {
            System.out.println("Klient się rozłączył");
        } finally {
            if (login != null && !login.isEmpty() && chatServer.usersMap.containsKey(this.login)) {
                chatServer.usersMap.remove(login);
                chatServer.broadcast(">>>User o loginie opuscil chat " + this.login + "<<<", this.login);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }


    public void sendMessage(String message) {
        out.println(message);
    }
}