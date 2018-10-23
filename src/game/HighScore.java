package game;

import java.io.*;

class HighScore {

    private static final File scoreFile = new File("score.txt");
    
    // Récupère le score présent dans le fichier score.txt
    public static int getHighScore() { 
        int currentHighScore = 0;

        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
                writeNewScore(0);
            } catch (IOException ex) {
                System.err.println("ERROR creating file");
            }

        } else { // Si le fichier n'existe pas, le score est forcément 0, donc on va pas rechercher le fichier.
            try {
                BufferedReader reader = new BufferedReader(new FileReader(scoreFile));
                String line = reader.readLine();

                currentHighScore = Integer.parseInt(line.trim());
                reader.close();
                System.out.println(currentHighScore);
            } catch (IOException ex) {
                System.err.println("ERROR reading scores from file");
            }
        }

        return currentHighScore;
    }
    
    //
    public static void setHighScore(int score) {
        if (score > getHighScore()) {
            System.out.println("NEW HIGHSCORE" + score + getHighScore());
            writeNewScore(score);
        }
    }

    private static void writeNewScore(int score) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile, false));// overwrite Highscore
            writer.write(score + "");
            writer.close();
        } catch (IOException ex) {
            System.err.println("ERROR setting scores to file");
        }
    }
}
