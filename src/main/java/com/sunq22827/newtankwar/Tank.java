package com.sunq22827.newtankwar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

class Tank {
    private static final int MOVE_SPEED = 5;
    private  int x;
    private  int y;
    private Direction direction;
    private boolean up, down , left, right;
    private boolean enemy;
    private boolean live = true;

    private int hp = 100;

    int getHp() {
        return hp;
    }

    void setHp(int hp) {
        this.hp = hp;
    }

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

    boolean isEnemy() {
        return enemy;
    }



    Tank(int x, int y, Direction direction) {
        this(x,y, direction, false);
    }

    Tank(int x, int y, Direction direction, boolean enemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }

    private void move(){
        if(this.stopped) return;
        x += direction.xFactor * MOVE_SPEED;
        y += direction.yFactor * MOVE_SPEED;
    }

    Image getImage(){
        String prefix = enemy ? "e" : "";
        return direction.getImage(prefix + "tank");
    }

    void draw (Graphics g){
        int oldX = x, oldY = y;
        this.determineDirection();
        this.move();

        if(x < 0) x=0;
        else if(x >800 - getImage().getWidth(null)) x=800-getImage().getWidth(null);
        if (y<0) y=0;
        else if (y>600 - getImage().getHeight(null)) y=600 -getImage().getHeight(null);

        Rectangle rec = this.getRectangle();
        for (Wall wall: GameClient.getInstance().getWalls()) {
            if(rec.intersects(wall.getRectangle())){
                x = oldX;
                y = oldY;
                break;
            }
        }

        for(Tank tank : GameClient.getInstance().getEnemyTanks()){
            if(rec.intersects(tank.getRectangle())){
                x = oldX;
                y = oldY;
                break;
            }
        }
        g.drawImage(this.getImage(), this.x,this.y,null);
    }

    Rectangle getRectangle() {
        return new Rectangle(x,y,
                getImage().getHeight(null),
                getImage().getWidth(null));
    }

    void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP: up = true; break;
            case KeyEvent.VK_DOWN: down = true; break;
            case KeyEvent.VK_LEFT: left = true; break;
            case KeyEvent.VK_RIGHT: right = true; break;
            case KeyEvent.VK_CONTROL: fire(); break;
            case KeyEvent.VK_A: superFire();break;
        }
    }

    private void superFire() {
        for(Direction direction: Direction.values()){
            Missile missile = new Missile(x+ getImage().getWidth(null)/2 - 6,
                    y+ getImage().getHeight(null)/2 -6,enemy,direction);
            GameClient.getInstance().getMissiles().add(missile);
        }

        String audioFile = new Random().nextBoolean() ? "supershoot.aiff": "supershoot.wav";
        Tools.playAudio(audioFile);
    }

    private void fire() {
        Missile missile = new Missile(x+ getImage().getWidth(null)/2 - 6,
                y+ getImage().getHeight(null)/2 -6,enemy,direction);
        GameClient.getInstance().getMissiles().add(missile);

        String audioFile = new Random().nextBoolean() ? "supershoot.aiff": "supershoot.wav";
        Tools.playAudio("shoot.wav");
    }

    void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP: up = false; break;
            case KeyEvent.VK_DOWN: down = false; break;
            case KeyEvent.VK_LEFT: left = false; break;
            case KeyEvent.VK_RIGHT: right = false; break;
        }

    }


    private boolean stopped;
    private void determineDirection(){
        if(!up && !left && !down && !right){
            this.stopped = true;
        }else {
            if (up && !left && !down && !right) this.direction = Direction.UP;
            else if (up && left && !down && !right) this.direction = Direction.LEFT_UP;
            else if (!up && left && !down && !right) this.direction = Direction.LEFT;
            else if (!up && left && down && !right) this.direction = Direction.LEFT_DOWN;
            else if (!up && !left && down && !right) this.direction = Direction.DOWN;
            else if (!up && !left && down && right) this.direction = Direction.RIGHT_DOWN;
            else if (!up && !left && !down && right) this.direction = Direction.RIGHT;
            else if (up && !left && !down && right) this.direction = Direction.RIGHT_UP;

            this.stopped = false;
        }

    }

}
