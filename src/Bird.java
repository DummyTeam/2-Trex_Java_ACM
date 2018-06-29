import acm.util.MediaTools;

import java.awt.*;
import java.util.ArrayList;

public class Bird extends Enemy {

    private int currentSpriteIndex = 0;
    private int runningAnimSpeed = DataProvider.getRunningAnimSpeed();
    private ArrayList<Image> spriteFlipImages;
    private Player player;

    Bird(Player player) {
        super(DataProvider.getBirdUp());
        scale(DataProvider.getBirdScale());
        this.player = player;
        initSpritePaths();
    }

    private void initSpritePaths() {
        ArrayList<String> flipPaths = DataProvider.getBirdSpriteList();

        spriteFlipImages = new ArrayList<>();

        for (String str : flipPaths) {
            spriteFlipImages.add(MediaTools.loadImage(str));
        }
    }



    @Override
    public void animate() {
        if (currentSpriteIndex % runningAnimSpeed == 0) {
            setImage(spriteFlipImages.get(currentSpriteIndex / runningAnimSpeed));
            scale(DataProvider.getBirdScale());
        }

        currentSpriteIndex = (currentSpriteIndex + 1) % (spriteFlipImages.size() * runningAnimSpeed);
    }

}
