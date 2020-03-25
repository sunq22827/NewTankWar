package com.sunq22827.newtankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

class GameClient extends JComponent {

    private static final GameClient INSTANCE = new GameClient();
    public static GameClient getInstance(){
        return INSTANCE;
    }
    private Tank playerTank;
    private List<Tank> enemyTanks;
    private final AtomicInteger enemyKilled = new AtomicInteger(0);
    private List<Wall> walls;
    private List<Missile> missiles;
    private List<Explosion> explosions;
    private Blood blood;

    private  static  final Random RANDOM = new Random();
    Blood getBlood() {
        return blood;
    }

    void addExplosion(Explosion explosion){
        explosions.add(explosion);
    }

    void add(Missile missile){
        missiles.add(missile);
    }

    Tank getPlayerTank() {
        return playerTank;
    }

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
        this.missiles = new CopyOnWriteArrayList<>();
        this.explosions = new ArrayList<>();
        this.blood = new Blood(400,300);
        this.walls = Arrays.asList(
                new Wall(180, 40, true, 17),
                new Wall(180, 530, true, 17),
                new Wall(110, 60, false, 16),
                new Wall(730, 60, false, 16)
        );

        this.initEnemyTanks();

        this.setPreferredSize(new Dimension(800,600));

    }

    private void initEnemyTanks() {
        this.enemyTanks = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                this.enemyTanks.add(new Tank(150+100*j,400+40*i, Direction.UP,true));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,800,600);
        if(!playerTank.isLive()){
            g.setColor(Color.RED);
            g.setFont(new Font(null, Font.BOLD, 80));
            g.drawString("GAME OVER",100,300);

            g.setFont(new Font(null, Font.BOLD, 60));
            g.drawString("PRESS F7 TO RESTART",70,400);
        } else {

            g.setColor(Color.WHITE);
            g.setFont(new Font(null, Font.BOLD, 10));
            g.drawString("Missiles" + missiles.size(),10,40);
            g.drawString("Explosions" + explosions.size(),10,60);
            g.drawString("Player Tank Hp" + playerTank.getHp(),10,80);
            g.drawString("Enemy Left" + enemyTanks.size(),10,100);
            g.drawString("Enemy Killed" + enemyKilled.get(),10,120);

            g.drawImage(Tools.getImage("tree.png"),10,510,null);

            playerTank.draw(g);
            if(playerTank.isDying() && RANDOM.nextInt(4) < 2){
               blood.setLive(true);
            }
            if(blood.isLive()){
                blood.draw(g);
            }

            int count = enemyTanks.size();
            enemyTanks.removeIf(t -> !t.isLive());
            enemyKilled.addAndGet(count - enemyTanks.size());
            for(Tank tank: enemyTanks){
                tank.draw(g);
            }
            for(Wall wall: walls){
                wall.draw(g);
            }

            missiles.removeIf(m ->!m.isLive());
            if(enemyTanks.isEmpty()){
                this.initEnemyTanks();
            }
            for(Missile missile: missiles){
                missile.draw(g);
            }

            explosions.removeIf(e ->!e.isLive());
            for(Explosion explosion: explosions){
                explosion.draw(g);
            }
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
            try {
                client.repaint();
                if(client.playerTank.isLive()){
                    for (Tank tank:client.enemyTanks){
                        tank.actRandomly();
                    }
                }

                Thread.sleep(50);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    void restart() {
        if(!playerTank.isLive()){
            playerTank = new Tank(400,200,Direction.DOWN,false);
        }
        this.initEnemyTanks();
    }
}
