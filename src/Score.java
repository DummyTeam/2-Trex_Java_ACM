import acm.graphics.GLabel;

import java.awt.*;

public class Score extends GLabel {
    private Game game;
    private long scoreValue = 1;
    private long lastLevelBarier = 50;
    private long heighestScore = 0;

    Score(Game game) {
        super("Score: 0");
        setColor(Color.GRAY);
        setFont(new Font("Arial", Font.BOLD, 20));
        setLocation(game.getWidth() - getWidth() - 60, 45);

        heighestScore = DataProvider.getHighestScore();

        this.game = game;
        game.add(this);
    }

    void animate() {
        if (scoreValue % 10 == 0) {
            heighestScore = DataProvider.getHighestScore();

            if (heighestScore != 0) {
                setLabel("Highest: " + heighestScore + "  Score: " + String.valueOf(scoreValue / 10));
                setLocation(game.getWidth() - getWidth() - 60, 45);
            } else {
                setLabel("Score: " + String.valueOf(scoreValue / 10));
            }

        }
        scoreValue++;

        if (lastLevelBarier * 2 == scoreValue / 10) {
            lastLevelBarier = scoreValue / 10;
            game.levelUp();
        }
    }

    int getScore() {
        return (int) scoreValue / 10;
    }
}
