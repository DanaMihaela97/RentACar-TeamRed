package org.example.payment;

public class Client implements Observer{
    private String getLatestNotify;

    @Override
    public String getNotify() {
        return getLatestNotify;
    }
}
