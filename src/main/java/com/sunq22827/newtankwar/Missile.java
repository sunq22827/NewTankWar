package com.sunq22827.newtankwar;

import javax.swing.*;
import java.awt.*;

public class Missile {
    private static final int SPEED = 10;
    private int x;
    private int y;

    private final boolean enemy;
    private final Direction direction;

    public Missile(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    private Image getImage(){
        return direction.getImage("missile");
    }

    private void move(){
        x += direction.xFactor * SPEED;
        y += direction.yFactor * SPEED;
    }

    void draw(Graphics g) {
        this.move();
        if(x<0 || x>800 || y<0 || y>600){
            return;
        }
       g.drawImage(this.getImage(),x,y,null);
    }
}
