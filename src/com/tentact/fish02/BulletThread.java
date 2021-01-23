package com.tentact.fish02;

public class BulletThread extends Thread{
    Bullet bullet;
    public BulletThread(Bullet bullet){
        this.bullet =bullet;
    }

    @Override
    public void run() {
        while (true){
            bullet.moving();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
