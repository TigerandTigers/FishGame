package com.tentact.fish02;

import org.omg.CORBA.LongLongSeqHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Pool extends JPanel {
    BufferedImage poolImg;

    Fish[] allFish = new Fish[29];

    Net net = new Net();

    int fenShu;

    BufferedImage fstImg;
    BufferedImage ptImg;

    int mouseX = 0;
    int mouseY = 0;
    int ptX = 416;
    int ptY = 400;
    double jiaoDu;

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    void getImg() throws IOException {
        getPoolImg();
        getFstImg();
        getPtImg();
        getFishImg();
        getNetImg();
    }

    private void getPtImg() throws IOException {
        File file = new File("src/pt.png");
        ptImg = ImageIO.read(file);
    }

    private void getFstImg() throws IOException {
        File file = new File("src/fst.png");
        fstImg = ImageIO.read(file);
    }

    private void getNetImg() throws IOException {
        net.getImg();
    }

    private void getFishImg() throws IOException {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                Fish fish = new Fish();
                fish.getImg("fish0" + (j + 1));
                allFish[count++] = fish;
            }
        }
        Fish fish27 = new Fish();
        fish27.getImg("fish13");
        Fish fish28 = new Fish();
        fish28.getImg("fish14");
        allFish[count++] = fish27;
        allFish[count++] = fish28;
    }

    private void getPoolImg() throws IOException {
        File file = new File("src/bg.jpg");
        poolImg = ImageIO.read(file);
    }

    @Override
    public void paint(Graphics g) {

        g.drawImage(poolImg, 0, 0, null);
        drawAllFish(g);
        drawNet(g);

        g.drawImage(fstImg,10,400,null);
        drawBullet(g);
        drawMess(g);
        drawPt(g);

    }

    private void drawBullet(Graphics g) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(bullet.isLive){
                bullet.drawBullet(g);
            }else{
                bullets.remove(bullet);
            }

        }
    }

    private void drawMess(Graphics g) {
        g.setFont(new Font("微软雅黑",Font.BOLD,21));
        g.setColor(Color.red);
        g.drawString(String.valueOf(fenShu%10),148,464);

        g.drawString(String.valueOf(fenShu%100/10),123,464);

        g.drawString(String.valueOf(fenShu%1000/100),100,464);

        g.drawString(String.valueOf(fenShu%10000/1000),77,464);

        g.drawString(String.valueOf(fenShu%100000/10000),54,464);

        g.drawString(String.valueOf(fenShu%1000000/100000),31,464);

        g.drawString("子弹数量"+bullets.size(),30,30);
    }

    private void drawPt(Graphics g) {
        double x1 = mouseX-ptX;
        double y1 = mouseY-ptY;
        jiaoDu = -Math.atan(x1/y1);
        Graphics2D gp = (Graphics2D) g;
        gp.rotate(jiaoDu,ptX+ptImg.getWidth()/2,ptY+ptImg.getHeight());
        gp.drawImage(ptImg,ptX,ptY,null);
    }

    private void drawNet(Graphics g) {
        if (net.flag) {
            g.drawImage(net.netImg, net.x, net.y, null);
        }
    }

    private void drawAllFish(Graphics g) {
        for (int i = 0; i < allFish.length; i++) {
            g.drawImage(allFish[i].fishImg, allFish[i].x, allFish[i].y, null);
        }
    }

    void action() {
        allFishStart();
        mouseThing();
        LongLongPaint();

    }

    private void mouseThing() {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                net.x = e.getX() - net.netImg.getWidth() / 2;
                net.y = e.getY() - net.netImg.getHeight() / 2;

                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                Point netPoint = new Point(x, y);

                catchFish(netPoint);

                Bullet bullet = new Bullet(Pool.this);
                bullet.imageIcon = new ImageIcon("src/bullet.png");

                bullet.x = ptX + 10;
                bullet.y = ptY + 20;
                bullet.roate = jiaoDu;

                bullet.p = new Point(ptX +19,ptY +39);
                if(bullets.size()<3){
                    bullets.add(bullet);
                    BulletThread thread = new BulletThread(bullet);
                    thread.start();
                }
            }

            private void catchFish(Point netPoint) {
                for (int i = 0; i < allFish.length; i++) {
                    Fish fish = allFish[i];
                    Point fishPoint = new Point(fish.x+fish.fishImg.getWidth()/2, fish.y+fish.fishImg.getHeight()/2);
                    boolean flag = checkCatchFish(netPoint, fishPoint);
                    if (flag) {
                        yanChi_CXU(fish);
                        fish.huoZhe = false;
                        fish.speed = 1;

                    }
                }
            }

            private void yanChi_CXU(Fish fish) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        fish.removing();
                        if (fish.fishImg.getWidth() < 200) {
                            fenShu += 5;
                        } else {
                            fenShu += 10;
                        }
                        if(fenShu>1000){
                            File file = new File("src/t1.jpg");
                            try {
                                poolImg = ImageIO.read(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        timer.cancel();
                    }
                }, 1000);
            }

            private boolean checkCatchFish(Point netPoint, Point fishPoint) {
                int juLi = (int) netPoint.distance(fishPoint);
                if (juLi <= net.netImg.getWidth() / 2) {
                    return true;
                }
                return false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                net.flag = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                net.flag = false;
            }
        };

        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);
    }

    private void LongLongPaint() {
        while (true) {
            repaint();
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void allFishStart() {
        for (int i = 0; i < allFish.length; i++) {
            allFish[i].start();
        }
    }


}
