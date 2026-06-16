package RozbudowaneStrukturyJava.SerwerGUi;


import javafx.application.Platform;

public class ClientReceiver {
    private ChatClientApp gui;

    public ClientReceiver(ChatClientApp gui) {
        this.gui = gui;
    }

    public void handle(String rawMessage) {

        Platform.runLater(() -> {
            if (rawMessage.startsWith("/msg ")) {
                gui.appendMessage(rawMessage.substring(5));

            } else if (rawMessage.startsWith("/login ")) {
                String user = rawMessage.substring(7);
                gui.appendMessage(">>> " + user + " dołączył do czatu.");
                gui.addUser(user);

            } else if (rawMessage.startsWith("/logout ")) {
                String user = rawMessage.substring(8);
                gui.appendMessage("<<< " + user + " opuścił czat.");
                gui.removeUser(user);

            } else if (rawMessage.startsWith("/online ")) {
                String[] users = rawMessage.substring(8).split(",");
                gui.updateUsers(users);
            }
        });
    }
}