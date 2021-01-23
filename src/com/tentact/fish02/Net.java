package com.tentact.fish02;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Net {
    BufferedImage netImg;
    int x;
    int y;
    boolean flag = false;
    void getImg() throws IOException {
        File file = new File("src/net09.png");
        netImg = ImageIO.read(file);
    }
}
