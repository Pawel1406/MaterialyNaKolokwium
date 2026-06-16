package RozbudowaneStrukturyJava.SerwerGUi;


import java.io.*;
import java.net.Socket;
import java.net.ConnectException;

public class ConnectionHandler {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ClientReceiver receiver;
    private ChatClientApp gui;

    public ConnectionHandler(ChatClientApp gui) {
        this.gui = gui;
        this.receiver = new ClientReceiver(gui);

        try {
            this.socket = new Socket("localhost", 12345);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            new Thread(this::listen).start();

        } catch (ConnectException e) {
            gui.appendMessage("BŁĄD: Serwer nie odpowiada (Connection Refused).");
            System.err.println("Błąd połączenia: Serwer jest prawdopodobnie wyłączony.");
        } catch (IOException e) {
            gui.appendMessage("BŁĄD WE/WY: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void listen() {
        try {
            String line;

            while (in != null && (line = in.readLine()) != null) {
                receiver.handle(line);
            }
        } catch (IOException e) {
            gui.appendMessage("Utracono połączenie z serwerem.");
        } finally {
            closeEverything();
        }
    }


    public void login(String username) {
        if (out != null) {
            send("/login " + username);

            send("/get_online");
        }
    }


    public void send(String msg) {
        if (out != null) {
            out.println(msg);
        } else {
            System.err.println("Nie można wysłać wiadomości: Brak aktywnego połączenia.");
        }
    }

     void closeEverything() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}