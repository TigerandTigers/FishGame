package com.tentact.fish02;

import javafx.scene.layout.Background;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeWindow extends JFrame {
    BufferedImage welcomeImg;
    public WelcomeWindow() throws IOException {
        this.setSize(800,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("捕鱼达人");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        File file = new File("src/bg_1.jpg");
        welcomeImg = ImageIO.read(file);
        JPanel jPanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                g.drawImage(welcomeImg,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        this.add(jPanel);
        LodingLable lodingLable = new LodingLable(1000,30);
        this.add(lodingLable,BorderLayout.SOUTH);
        lodingLable.start();
        loding(this);
    }

    private void loding(WelcomeWindow welcomeWindow) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                welcomeWindow.setVisible(false);
                try {
                    new Windows().show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                timer.cancel();
            }
        },1000);
    }

    public static void main(String[] args) throws IOException {
        WelcomeWindow welcome = new WelcomeWindow();


        welcome.setVisible(true);

    }
}
