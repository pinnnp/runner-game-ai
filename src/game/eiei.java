package game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class eiei {
	
	public eiei() {
		JFrame jframe = new JFrame();
		jframe.setSize(500, 500);
		jframe.setResizable(false);
		jframe.setVisible(true);
		
		jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void main(String[] args) {
		new eiei();
	}
}
