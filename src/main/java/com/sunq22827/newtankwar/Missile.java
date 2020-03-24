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
        switch (direction){
            case UP:
                y-= SPEED;
                break;
            case LEFT_UP:
                x-= SPEED; y-= SPEED;
                break;
            case LEFT:
                x-= SPEED;
                break;
            case LEFT_DOWN:
                x-= SPEED; y+= SPEED;
                break;
            case DOWN:
                y+= SPEED;
                break;
            case RIGHT_DOWN:
                x+= SPEED; y+= SPEED;
                break;
            case RIGHT:
                x+= SPEED;
                break;
            case RIGHT_UP:
                x+= SPEED;y-= SPEED;
                break;

        }
    }

    void draw(Graphics g) {
        this.move();
        if(x<0 || x>800 || y<0 || y>600){
            return;
        }
       g.drawImage(this.getImage(),x,y,null);
    }
}
