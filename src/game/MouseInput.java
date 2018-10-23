package game;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


class MouseInput implements MouseListener {

    @Override
    public void mousePressed(MouseEvent e) {
        if (Runner.state == Runner.STATE.MENU) {
            System.out.println(Runner.state);
            System.out.println(e.getButton());
            int x = e.getX() - 3;
            int y = e.getY() - 22;

            //play
            if (Menu.playButton.contains(x, y)) {
                Runner.state = Runner.STATE.GAME;
                Runner.start();
            } else if (Menu.highscoreButton.contains(x, y)) {
                Runner.menu.displayScores();
            } else if (Menu.quitButton.contains(x, y)) {
                Runner.quit();
            }
        } else {
            Runner.game.mousePressed(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Runner.game.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

}
