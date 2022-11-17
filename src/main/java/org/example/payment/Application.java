package org.example.payment;

public class Application implements Observable {
    private String notify;
    @Override
    public String notifyClient() {
        return notify;
    }
}
