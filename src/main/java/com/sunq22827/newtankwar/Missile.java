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

    Image getImage(){
        switch (direction){
            case UP:
                return new ImageIcon("assets/images/missileU.gif").getImage();
            case UPLEFT:
                return new ImageIcon("assets/images/missileLU.gif").getImage();
            case LEFT:
                return new ImageIcon("assets/images/missileL.gif").getImage();
            case DOWNLEFT:
                return new ImageIcon("assets/images/missileLD.gif").getImage();
            case DOWN:
                return new ImageIcon("assets/images/missileD.gif").getImage();
            case DOWNRIGHT:
                return new ImageIcon("assets/images/missileRD.gif").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/missileR.gif").getImage();
            case UPRIGHT:
                return new ImageIcon("assets/images/missileRU.gif").getImage();
        }
        return null;
    }

    private void move(){
        switch (direction){
            case UP:
                y-= SPEED;
                break;
            case UPLEFT:
                x-= SPEED; y-= SPEED;
                break;
            case LEFT:
                x-= SPEED;
                break;
            case DOWNLEFT:
                x-= SPEED; y+= SPEED;
                break;
            case DOWN:
                y+= SPEED;
                break;
            case DOWNRIGHT:
                x+= SPEED; y+= SPEED;
                break;
            case RIGHT:
                x+= SPEED;
                break;
            case UPRIGHT:
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
