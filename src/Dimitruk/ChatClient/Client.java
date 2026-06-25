package Dimitruk.ChatClient;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class Client implements Runnable{
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    private Consumer<String> onMessageReceived;

    public void setOnMessageReceived(Consumer<String> onMessageReceived) {
        this.onMessageReceived = onMessageReceived;
    }

    public Client(String address, int port) throws IOException {
        socket = new Socket(address, port);
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        writer = new PrintWriter(output, true);
    }

    public void send(String message) {
        writer.println(message);
    }

    @Override
    public void run() {
        String message;
        try{
            while((message = reader.readLine()) != null) {
                onMessageReceived.accept(message);
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
