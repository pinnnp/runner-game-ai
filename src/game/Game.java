package game;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Poulet
 */

public class Game implements Runnable {

    public final Controller c;
    public final Player player;
    private int ticks;
    private int score;
    private int level = 0;
    
    public static ArrayList<Integer> in = new ArrayList<>();
    public static int pSpeed = 10;
    public static int pColumnx;
    public static int pColumny;
    public static int pColumnh;
    public static int hasHole = 0;
    public static int jumppable = 0;
    private int minX = 0;
    private int minIndex;
    public static int speed;
    public Game() {
    	in = new ArrayList<Integer>();
        c = new Controller();
        player = new Player(Runner.WIDTH / 3, Runner.HEIGHT / 2, 20, 40);
        score = 0;
        in = new ArrayList<Integer>(Arrays.asList(10,pColumnx,hasHole));
        //add level from Controller that is obstacle class
        for (int i = 0; i < 5; i++) {
            c.addColumn(0);
        }
    }

    @Override
    public void run() {
        speed = 10; //initial speed
        //System.out.println("Game.run()");
        
        while (Runner.state == Runner.STATE.GAME) {
            ticks++;

            for (int i = 0; i < c.columns.size(); i++) {
                Rectangle column = c.columns.get(i);
                column.x -= speed;
                if(column.x > player.x) {
                	if (minX == 0) {minX = column.x; minIndex = i;}
                	else if (minX > column.x) {minX = column.x; minIndex = i;}
                }
                
            }
            minX = 0;
            Rectangle columni = c.columns.get(minIndex);
            pColumnx = columni.x;
            pColumny = columni.y;
            pColumnh = columni.height;
            jumppable = columni.height > 35 ? 0:1;
            hasHole = Runner.HEIGHT - pColumnh - 120 - pColumny == 20 ? 1 : 0;
            
            if (ticks % 2 == 0 && player.ymotion < 15) {
                player.ymotion += 2;
                //System.out.println("" + ticks + " " + player.ymotion);
            }
            
            // if reach 500 ticks then level up speed * 1.2
            if (ticks % 500 == 0) {
                level++;
                speed = (int) round(speed * 1.2);
                pSpeed = speed;
            }
            
            for (int i = 0; i < c.columns.size(); i++) {
                Rectangle column1 = c.columns.get(i);

                if (column1.x + column1.width < 0) {
                    c.columns.remove(column1);
                    c.addColumn(1);

                    if (column1.y == 0) {
                        c.addColumn(1);
                    }
                }
            }

            // actualise la position
            player.y += player.ymotion;

            for (Rectangle column : c.columns) {
                if (column.intersects(player)) {
                    Runner.state = Runner.STATE.OVER;

                    player.x = column.x - player.width;
                }
            }

            // stoppe player au niveau du sol en fonction de sa position
            if ((player.y >= Runner.HEIGHT - 160 || player.y < 0) && !player.crouched) {
                player.y = Runner.HEIGHT - 160;
//                System.out.println("HELLO160");
                player.jumping = 0;
            } else if ((player.y >= Runner.HEIGHT - 140 || player.y < 0) && player.crouched) {
            	//System.out.println("HELLO140");
                player.y = Runner.HEIGHT - 140;
                player.jumping = 0;
            }
            
            // actualise le rendu tout les deux ticks
            if (ticks % 2 == 0) {
                Runner.renderer.repaint();
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Runner.renderer.repaint();
        //System.out.println("Game.run() end");
    }

    void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Runner.WIDTH, Runner.HEIGHT);

        g.setColor(Color.black);
        g.fillRect(0, Runner.HEIGHT - 120, Runner.WIDTH, Runner.HEIGHT - 120);

        g.setColor(Color.white);
        g.fillRect(0, Runner.HEIGHT - 120, Runner.WIDTH, 20);

        g.setColor(Color.white);
        g.fillRect(player.x, player.y, player.width, player.height);

        for (Rectangle column : c.columns) {
            c.paintColumn(g, column);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));

        if (Runner.state == Runner.STATE.OVER) {
            g.drawString("GameOver", 75, Runner.HEIGHT / 2);
            HighScore.setHighScore(score);
        }

        g.setFont(new Font("Arial", 1, 50));

        if (Runner.state == Runner.STATE.GAME) {
            score++;
        }
        String scoreString = "Score : " + score;
        g.drawString(scoreString, 20, Runner.HEIGHT - 50);
        
        String levelString = "Level : " + level;
        g.drawString(levelString, Runner.WIDTH - 250, Runner.HEIGHT - 50);
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("clicked");
        switch (Runner.state) {
            case GAME:
                System.out.println(Runner.state);
                // si clic gauche
                if (e.getButton() == 1) {
                    player.jump();
                    player.uncrouch();
                } else if (player.jumping == 0) {
                    player.crouch();
                }
                break;
            case OVER:
                System.out.println(Runner.state);
                Runner.state = Runner.STATE.MENU;
                Runner.renderer.repaint();
                break;
        }
    }
    
    public void mousePressed(int i) {
        //System.out.println("clicked");
        switch (Runner.state) {
            case GAME:
                //System.out.println(Runner.state);
                // si clic gauche
                if (i == 1) {
                    player.jump();
                    player.uncrouch();
                } else if (player.jumping == 0) {
                    player.crouch();
                }
                break;
//            case OVER:
//                System.out.println(Runner.state);
//                Runner.state = Runner.STATE.MENU;
//                Runner.renderer.repaint();
//                break;
        }
    }
    
    public void mousePressed(InputEvent e) {
        System.out.println("clicked");
        switch (Runner.state) {
            case GAME:
                System.out.println(Runner.state);
                // si clic gauche
                if (e.equals(InputEvent.BUTTON1_DOWN_MASK)) {
                    player.jump();
                    player.uncrouch();
                } else if (player.jumping == 0) {
                    player.crouch();
                }
                break;
            case OVER:
                System.out.println(Runner.state);
                Runner.state = Runner.STATE.MENU;
                Runner.renderer.repaint();
                break;
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        // si clic droit pendant l'état de jeu
        if (e.getButton() == 3 && player.crouched) {
            player.crouch();
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        System.out.println("a");
    }
    
    public int getScore() {
    	return this.score;
    }
    
    public int getTicks() {
    	return ticks;
    }
}
