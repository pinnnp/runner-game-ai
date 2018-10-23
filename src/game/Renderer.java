package game;

import javax.swing.*;
import java.awt.*;

//paint each state
public class Renderer extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (Runner.state) {
            case GAME:
            case OVER:
                Runner.game.render(g);
                break;
            case MENU:
                Runner.menu.render(g);
                break;
        }

    }

}
