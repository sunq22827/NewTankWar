package com.sunq22827.newtankwar;

import javax.swing.*;
import java.awt.*;

public class Missile {
    private static final int SPEED = 10;
    private int x;
    private int y;

    private final boolean enemy;
    private final Direction direction;

    private boolean live = true;

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

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
            this.live = false;
            return;
        }

        Rectangle rectangle = this.getRectangle();
        for (Wall wall: GameClient.getInstance().getWalls()) {
            if(rectangle.intersects(wall.getRectangle())){
               this.setLive(false);
               return;
            }
        }


        if(enemy){
            Tank playerTank = GameClient.getInstance().getPlayerTank();
                if(rectangle.intersects(playerTank.getRectangleForHitDetection())){
                    addExplosion();
                    playerTank.setHp(playerTank.getHp()- 20);
                    if(playerTank.getHp() <= 0){
                        playerTank.setLive(false);
                    }
                    this.setLive(false);
                }

        } else {
            for(Tank tank: GameClient.getInstance().getEnemyTanks()){
                if(rectangle.intersects(tank.getRectangleForHitDetection())){
                    addExplosion();
                    tank.setLive(false);
                    this.setLive(false);
                    break;
                 }
            }
        }

       g.drawImage(this.getImage(),x,y,null);
    }

    private void addExplosion(){
          GameClient.getInstance().addExplosion(new Explosion(x,y));
          Tools.playAudio("explode.wav");
    }

    Rectangle getRectangle() {
        return new Rectangle(x,y,
                getImage().getHeight(null),
                getImage().getWidth(null));
    }
}
