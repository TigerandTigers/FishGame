package com.tentact.fish02;

import java.util.Timer;
import java.util.TimerTask;

public class Test {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hee");
                timer.cancel();
            }
        },5000);
    }
}
