package game;

import java.awt.*;
import javax.swing.JOptionPane;


class Menu {

    public static final Rectangle playButton = new Rectangle(Runner.WIDTH / 2, 200, 140, 50);
    public static final Rectangle highscoreButton = new Rectangle(Runner.WIDTH / 2, 300, 140, 50);
    public static final Rectangle quitButton = new Rectangle(Runner.WIDTH / 2, 400, 140, 50);

    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("RUNNER", Runner.WIDTH / 2 - 60, 100);

        g2d.setColor(Color.blue);
        g.setFont(fnt0.deriveFont(38f));
        g.drawString("Play", playButton.x + 10, playButton.y + 40);
        g.drawString("Score", highscoreButton.x + 10, highscoreButton.y + 40);
        g.drawString("Quit", quitButton.x + 10, quitButton.y + 40);
        g2d.draw(playButton);
        g2d.draw(highscoreButton);
        g2d.draw(quitButton);
    }
    
    public void displayScores() {
        JOptionPane.showMessageDialog(null, "the highest score right now is\n" + HighScore.getHighScore());
    }
}
