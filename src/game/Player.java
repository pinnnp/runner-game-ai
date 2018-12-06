package game;

import java.awt.*;

//player class
public class Player extends Rectangle {

    public int ymotion;
    public int jumping = 0; // etat du saut 0: au sol, 1: premier saut, 2: double saut
    public boolean crouched = false; // false: player debout, true: player accroupi

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    // fait sauter player
    public void jump() {
    	//System.out.println(jumping);
        if (ymotion > 0) {
            ymotion = 0;
        }
        if (jumping < 2) {
            jumping++;
            ymotion -= 10;
        }
        if (ymotion < -10) {
        	ymotion = -10;
        }
    }

    // player crouch
    public void crouch() {
    	if (!crouched) {
            this.height -= 20;
            this.y = 460;
            //System.out.println("CROUCHED : " + height);
            this.crouched = true;
    	} else {
            uncrouch();
        }
    }
    
    public void uncrouch() {
        if (crouched) {
            this.crouched = false;
            this.height += 20;
            this.y = 440;
        }
    }
    
}
