package com.sunq22827.newtankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameClient extends JComponent {

    private Tank playerTank;
    public GameClient() {
        this.playerTank = new Tank(400,200,Direction.DOWN);
        this.setPreferredSize(new Dimension(800,600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        playerTank.draw(g);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("DIY好玩无用的小游戏");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        final GameClient client = new GameClient();
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
