package Podstawy_Javy.WzorceProjektowe.Factory;

public class SMSNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Wysyłanie powiadomienia SMS...");
    }
}