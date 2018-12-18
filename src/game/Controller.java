
package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

//obstacle class
public class Controller {
    
    public ArrayList<Rectangle> columns = new ArrayList<>();
    public Random random = new Random(4);
    public int hole;
    
    public Controller() {
    }
    
    public void addColumn(int level) {
        //int space = 300;
        int width = 10;
        int height = 10;
        
        int rWidth = 10;
        
        // une chance sur deux d'obtenir un trou sous la colonne
        hole = random.nextBoolean() ? 20 : 0;
        
        switch (level) {
            case 0:
                height = 10 + random.nextInt(40);
                rWidth = Runner.WIDTH + width + columns.size() * 900;
                break;
            case 1:
                height = 10 + random.nextInt(60);
                rWidth = columns.get(columns.size()-1).x + 600;
                break;
            case 2:
                height = 10 + random.nextInt(80);
                rWidth = columns.get(columns.size()-1).x + 600;
                break;
            case 3:
                height = 10 + random.nextInt(100);
                rWidth = columns.get(columns.size()-1).x + 600;
                break;
            case 4:
                height = 10 + random.nextInt(120);
                rWidth = columns.get(columns.size()-1).x + 600;
                break;
        }
        
        int rHeight = Runner.HEIGHT - height - 120 - hole;
        columns.add(new Rectangle(rWidth, rHeight, width, height));
    }
      
    public void paintColumn(Graphics g, Rectangle column) {
        g.setColor(Color.yellow.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }
    
    public int haveHole() {
    	if (hole == 0) return 0;
    	else return 1;
    }
}
