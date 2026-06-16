package RozbudowaneStrukturyJava.Serwer;

import java.net.*;
import java.io.*;

public class Client{
    private final static int PORT=12345;
    private final static String HOST="localhost";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            System.out.println("Połączono z serwerem chatu");
            System.out.println("Podaj login: ");
            Thread reader = new Thread(new ReadThread(socket));
            reader.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String userInput;
            while ((userInput = in.readLine()) != null) {
                out.println(userInput);
                if(userInput.equalsIgnoreCase("/close")){
                    return;
                }
            }
        }
        catch (IOException e) {
            e.getMessage();
        }

    }
    public static class ReadThread implements Runnable{
        private final Socket socket;
        public ReadThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String userInput;
                while ((userInput = in.readLine()) != null) {
                    System.out.println(userInput);
                }
            }
            catch (IOException e) {
                System.out.println("Połączenie zerwane!!!");
            }
        }
    }
}
