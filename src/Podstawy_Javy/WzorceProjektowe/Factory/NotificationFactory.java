package Podstawy_Javy.WzorceProjektowe.Factory;

public class NotificationFactory {
    public Notification createNotification(String channel) {
        if (channel == null || channel.isEmpty()) {
            return null;
        }
        return switch (channel.toUpperCase()) {
            case "EMAIL" -> new EmailNotification();
            case "SMS"   -> new SMSNotification();
            default      -> throw new IllegalArgumentException("Nieznany kanał: " + channel);
        };
    }

    static void main() {
        NotificationFactory factory = new NotificationFactory();
        factory.createNotification("EMAIL");
        factory.createNotification("SMS");
    }
}