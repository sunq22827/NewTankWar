package com.sunq22827.newtankwar;

import java.awt.*;

public class Blood {
    private int x,y;
    private final Image image;

    private boolean live = true ;

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

    Blood(int x, int y) {
        this.x = x;
        this.y = y;
        this.image = Tools.getImage("blood.png");
    }

    void draw(Graphics g){
        g.drawImage(image,x,y,null);
    }

    Rectangle getRectangle() {
       return new Rectangle(x,y,image.getWidth(null),image.getHeight(null));
    }
}
