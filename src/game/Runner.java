package game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import javax.swing.*;

import ai.*;

//main class / game window / use to run the game
public class Runner {

	public static final int WIDTH = 1300, HEIGHT = 600;
	public static Game game;
	public static Menu menu;
	public static Renderer renderer;
	public static STATE state = STATE.GAME;
	public static Driver driver; // *
	

	public Runner() {

		JFrame jframe = new JFrame();
		renderer = new Renderer();

		//menu = new Menu();

		jframe.add(renderer);

		//jframe.addMouseListener(new MouseInput());

		jframe.setTitle("Runner");
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.setResizable(false);
		jframe.setVisible(true);
		
		Runnable t = new Runnable() {
			public void run() {
				while (true) {
					if (Runner.state == Runner.STATE.OVER) {
						System.out.println("Dispatch");
						jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
						return;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						
					}
				}
			}
		};
		
		new Thread(t).start();
		
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
		MENU, GAME, OVER
	}

	public static void close(JFrame jframe) {

		jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));

	}
}
