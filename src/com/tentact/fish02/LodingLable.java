package com.tentact.fish02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LodingLable extends JLabel implements ActionListener {
    int width = 0;
    int height = 0;
    int nowX = 0;
    int nowY = 0;
    boolean isLeft = false;
    boolean isBeginRoll = false;
    Timer timer = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isLeft) {
            if (nowX <= getWidth()) {
                nowX += 5;
            } else {
                isLeft = false;
            }
        } else {
            if (nowX >= -1 * width) {
                nowX -= 5;
            } else {
                isLeft = true;
            }
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (isBeginRoll) {
            g2.setColor(Color.white);
            g2.fillRect(0, 0, getWidth(), getHeight());
            if (isLeft) {
                g2.setPaint(new GradientPaint(nowX,0, Color.white,nowX+width, 0, Color.blue));
            } else {
                g2.setPaint(new GradientPaint(nowX,0, Color.blue,nowX+width, 0, Color.white));
            }

            g2.fillRect(nowX,nowY,width,height);
            g2.setColor(Color.white);
        }else {
            g2.setColor(Color.white);
        }
    }
    public LodingLable(int width, int height){
        setBackground(Color.white);
        this.width =(int) (width * 0.9);
        this.height = height;
        setSize(width, height);
        timer = new Timer(12,this);
        this.setText(" ");
    }

    public void start(){
        isBeginRoll = true;
        nowX = -1 * width;
        nowY = 0;
        timer.start();
    }


}
