package com.tentact.fish02;

import javax.swing.*;
import java.io.IOException;

public class Windows {


    public void show() throws IOException {
        JFrame window = new JFrame();
        window.setSize(800, 500);

        window.setLocationRelativeTo(null);
        window.setTitle("捕鱼达人");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Pool pool = new Pool();
        pool.getImg();
        window.add(pool);

        window.setVisible(true);
        pool.action();
    }
}
