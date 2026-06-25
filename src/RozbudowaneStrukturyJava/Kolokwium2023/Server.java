package RozbudowaneStrukturyJava.Kolokwium2023;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {

    private static final int PORT = 5000;
    // Współdzielona zmienna - jej wartość jest modyfikowana przez okienko z suwakiem
    public static volatile int currentRadius = 3;

    public static void main(String[] args) {
        // 1. Uruchamiamy serwer sieciowy w wątku pobocznym
        Thread networkThread = new Thread(Server::startListening);
        networkThread.setDaemon(true);
        networkThread.start();

        // 2. Uruchamiamy interfejs użytkownika na głównym wątku
        System.out.println("Otwieranie interfejsu graficznego serwera...");
        ServerApp.launchUI(args);
    }

    private static void startListening() {
        // Tworzymy katalog, jeśli go nie ma
        File imagesDir = new File("images");
        if (!imagesDir.exists()) imagesDir.mkdirs();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serwer sieciowy oczekuje na połączenia na porcie " + PORT);

            // Akceptowanie klientów jeden po drugim
            while (true) {
                System.out.println("Serwer");
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Połączono z klientem: " + clientSocket.getRemoteSocketAddress());
                    handleClient(clientSocket);
                } catch (Exception e) {
                    System.err.println("Błąd podczas obsługi klienta: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) throws Exception {
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        // Krok 1 i 2: Odbiór pliku i zapis ze znacznikiem czasu
        long fileSize = input.readLong();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String savedImagePath = "images/" + timestamp + ".png";
        File savedFile = new File(savedImagePath);

        try (FileOutputStream fos = new FileOutputStream(savedFile)) {
            byte[] buffer = new byte[8192];
            long receivedSize = 0;
            int count;
            while (receivedSize < fileSize && (count = input.read(buffer, 0, (int) Math.min(buffer.length, fileSize - receivedSize))) != -1) {
                fos.write(buffer, 0, count);
                receivedSize += count;
            }
        }
        System.out.println("Zapisano źródłowy plik klienta: " + savedImagePath);

        // Krok 3, 4, 5: Delegujemy przetwarzanie (Box Blur) i bazę danych do klasy ServerApp
        File processedFile = ServerApp.processAndSave(savedFile, savedImagePath, currentRadius);

        // Krok 6: Odesłanie pliku do klienta
        output.writeLong(processedFile.length());
        try (FileInputStream fis = new FileInputStream(processedFile)) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = fis.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
        }
        output.flush();
        System.out.println("Odesłano przetworzony obraz do klienta.\n---");

        // Sprzątanie pliku tymczasowego wygenerowanego do wysyłki
        if (processedFile.exists()) {
            processedFile.delete();
        }
    }
}