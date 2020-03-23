package com.sunq22827.newtankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GameClient extends JComponent {

    private static final GameClient INSTANCE = new GameClient();
    public static GameClient getInstance(){
        return INSTANCE;
    }
    private Tank playerTank;
    private List<Tank> enemyTanks;
    private List<Wall> walls;
    private List<Missile> missiles;

    List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    List<Wall> getWalls() {
        return walls;
    }

    List<Missile> getMissiles() {
        return missiles;
    }

    GameClient() {
        this.playerTank = new Tank(400,200,Direction.DOWN,false);
        this.enemyTanks = new ArrayList<>(18);
        this.missiles = new ArrayList<>();
        this.walls = Arrays.asList(
                new Wall(150, 40, true, 18),
                new Wall(150, 530, true, 18),
                new Wall(80, 60, false, 16),
                new Wall(730, 60, false, 16)
        );
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                this.enemyTanks.add(new Tank(150+100*j,400+40*i,Direction.UP,true));
            }
        }
        this.setPreferredSize(new Dimension(800,600));

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,800,600);
        playerTank.draw(g);

        for(Tank tank: enemyTanks){
            tank.draw(g);
        }
        for(Wall wall: walls){
            wall.draw(g);
        }
        for(Missile missile: missiles){
            missile.draw(g);
        }
    }


    public static void main(String[] args) {
        com.sun.javafx.application.PlatformImpl.startup(()->{});
        JFrame frame = new JFrame();
        frame.setTitle("DIY好玩无用的小游戏");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        final GameClient client = GameClient.getInstance();
        frame.add(client);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                client.playerTank.keyPressed(e);
                }


            @Override
            public void keyReleased(KeyEvent e) {
                client.playerTank.keyReleased(e);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //noinspection InfiniteLoopStatement
        while (true){
            client.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
