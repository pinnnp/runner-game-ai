package game;

import java.util.Arrays;
import javax.swing.*;

import ai.*;


//main class / game window / use to run the game
public class Runner {

    public static final int WIDTH = 1300, HEIGHT = 600;
    public static Game game;

    public static Menu menu;
    public static Renderer renderer;
    public static STATE state = STATE.MENU;
    public static Driver driver; //*

    public Runner() {
        JFrame jframe = new JFrame();

        renderer = new Renderer();

        menu = new Menu();

        jframe.add(renderer);

        jframe.addMouseListener(new MouseInput());

        jframe.setTitle("Runner");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setResizable(false);
        jframe.setVisible(true);
        
    }

    public static void start() {
        game = new Game(); // nouvelle instance de Game crée à chaque nouveau démarrage
        Thread t = new Thread(game);
        t.start();
    }

    public static void quit() {
        System.exit(0);
    }

    public static void main(String[] args) {
        new Runner();
    }

    public enum STATE {
        MENU,
        GAME,
        OVER
    }
    
}


