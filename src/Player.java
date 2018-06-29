import acm.util.MediaTools;
import acm.util.SoundClip;


import java.awt.*;
import java.util.ArrayList;

public class Player extends GameObject {

    private double acceleration = DataProvider.getAcceleration();
    private double initialVelocity = DataProvider.getInitialVelocity();
    private double time = DataProvider.getPlayerTimeRes();
    private double initialTime;
    private Ground ground;
    private Game game;
    private ArrayList<Image> spriteRunImages;
    private ArrayList<Image> spriteInclineImages;
    private int currentSpriteIndex = 0;
    private int runningAnimSpeed = DataProvider.getRunningAnimSpeed();
    private double lowestYPoint = 0;
    private double defaultPlayerHeight = 0;

    private boolean isJumping = false;
    private boolean isInclined = false;
    private SoundClip deathSound;
    private SoundClip jumpSound;


    Player(Game game, Ground ground) {
        super(DataProvider.getStaticPlayerImg());
        this.ground = ground;
        scale(DataProvider.getPlayerScale());

        initialTime = (2 * initialVelocity) / acceleration;
        int currentHeight = (int) (initialVelocity * initialTime - (acceleration * initialTime * initialTime) / 2);
        lowestYPoint = ground.getY() - getHeight() - currentHeight + 5;
        setLocation(DataProvider.getPlayerInitialXPos(), lowestYPoint);
        defaultPlayerHeight = getHeight();
        this.game = game;

        deathSound = new SoundClip(DataProvider.getDeathSound());
        deathSound.setVolume(2.0);

        jumpSound = new SoundClip(DataProvider.getJumpSound());
        jumpSound.setVolume(2.0);

        game.add(this);

        initSpritePaths();
    }

    private void initSpritePaths() {
        ArrayList<String> spritePaths = DataProvider.getPlayerSpriteList();
        ArrayList<String> spriteInclinePaths = DataProvider.getPlayerInclineList();

        spriteRunImages = new ArrayList<>();
        spriteInclineImages = new ArrayList<>();

        for (String str : spritePaths) {
            spriteRunImages.add(MediaTools.loadImage(str));
        }

        for (String str : spriteInclinePaths) {
            spriteInclineImages.add(MediaTools.loadImage(str));
        }
    }

    public double getDefaultPlayerHeight() {
        return defaultPlayerHeight;
    }


    private void buildJumpAnim() {
        int currentHeight = (int) (initialVelocity * time - (acceleration * time * time) / 2);

        if (time <= initialTime) {
            setLocation(getX(), ground.getY() - getHeight() - currentHeight + 6);
            time += DataProvider.getPlayerTimeRes();
        } else {
            time = DataProvider.getPlayerTimeRes();
            isJumping = false;
        }
    }

    private void buildRunAnim() {
        if (currentSpriteIndex % runningAnimSpeed == 0) {
            setImage(spriteRunImages.get(currentSpriteIndex / runningAnimSpeed));
            scale(DataProvider.getPlayerScale());
        }

        currentSpriteIndex = (currentSpriteIndex + 1) % (spriteRunImages.size() * runningAnimSpeed);
    }

    private void buildInclineAnim() {
        if (currentSpriteIndex % runningAnimSpeed == 0) {
            setImage(spriteInclineImages.get(currentSpriteIndex / runningAnimSpeed));
            scale(DataProvider.getPlayerScale());
        }

        currentSpriteIndex = (currentSpriteIndex + 1) % (spriteInclineImages.size() * runningAnimSpeed);
    }

    public void jump() {
        if (!isJumping) {
            jumpSound.play();
        }

        isJumping = true;
        setImage(DataProvider.getStaticPlayerImg());
        scale(DataProvider.getPlayerScale());
    }

    public void incline() {
        if (!isInclined) {
            setImage(spriteInclineImages.get(0));
            scale(DataProvider.getPlayerScale());
        }

        isInclined = true;

        initialTime = (2 * initialVelocity) / acceleration;
        int currentHeight = (int) (initialVelocity * initialTime - (acceleration * initialTime * initialTime) / 2);
        setLocation(DataProvider.getPlayerInitialXPos(), ground.getY() - getHeight() - currentHeight + 5);
    }

    public void stopIncline() {
        isInclined = false;
        setImage(DataProvider.getStaticPlayerImg());
        scale(DataProvider.getPlayerScale());

        setLocation(DataProvider.getPlayerInitialXPos(), lowestYPoint);
    }

    public void kill() {
        deathSound.play();

        if (!isInclined) {
            setImage(DataProvider.getDeadPlayerImg());
            scale(DataProvider.getPlayerScale());
        } else {
            initialTime = (2 * initialVelocity) / acceleration;
            int currentHeight = (int) (initialVelocity * initialTime - (acceleration * initialTime * initialTime) / 2);
            setLocation(DataProvider.getPlayerInitialXPos(), ground.getY() - getHeight() - currentHeight + 5);
        }

        DataProvider.writeScore(game.getGameScore());
    }



    public void animate() {
        if (isJumping) {
            buildJumpAnim();
        } else if (isInclined) {
            buildInclineAnim();
        } else {
            buildRunAnim();
        }
    }
}