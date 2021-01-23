package com.tentact.fish02;

import javax.swing.*;
import java.awt.*;

public class Bullet {
    int x;
    int y;
    Point p;
    ImageIcon imageIcon;
    double roate;
    Pool panel;
    boolean isLive = true;
    public Bullet(Pool pool){
        this.panel = pool;
    }
    void moving(){
        if(isLive){
            y -= 10;
            panel.repaint();
            if(y<=-100){
                isLive = false;
            }
        }
    }
    void drawBullet(Graphics g){
        Graphics2D gp = (Graphics2D)g.create();
        gp.rotate(roate,p.x,p.y);
        gp.drawImage(imageIcon.getImage(),x,y,panel);
    }
}
