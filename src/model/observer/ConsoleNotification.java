package model.observer;

public class ConsoleNotification implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Notification: " + message);
    }
}
