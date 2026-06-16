package Podstawy_Javy.WzorceProjektowe.Factory;

public class EmailNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Wysyłanie powiadomienia E-mail...");
    }
}


