package RozbudowaneStrukturyJava.Kolokwium2022;

public class Main {
    public static void main(String[] args) {
        WordBag wordBag = new WordBag();
        wordBag.populate();
        Server server = new Server(12345, wordBag);
        server.start();
        server.startSending();
    }
}