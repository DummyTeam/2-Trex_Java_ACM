import acm.program.*;
import acm.util.SoundClip;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Game extends GraphicsProgram {

    private volatile boolean isGameOver = false;
    private Player player;
    private SeamlessSlider backgroundSlider;
    private SeamlessSlider groundSlider;
    private EnemyHandler obstacleHandler;
    private Score score;
    private int gameLevel = 0;

    private GameObject gameOver;
    private GameObject replayBtn;
    private Thread mainThread;


    // Initializing objects
    @Override
    public void init() {
        super.init();
        DataProvider.readFromFile();

        //Setting window size
        setSize(DataProvider.getWindowSize()); // Bug in lib

        //Adding listeners
        addMouseListeners();
        addKeyListeners();


        //DataProvider.resetScoreFile();
        mainThread = new Thread(new MainLoopThread());

        // Assigning newly created objects to references
        Background background = new Background();
        backgroundSlider = new SeamlessSlider(this, background);

        Ground ground = new Ground();
        groundSlider = new SeamlessSlider(this, ground);
        groundSlider.setSliderSpeed(DataProvider.getGroundObstacleSpeed());

        player = new Player(this, ground);
        obstacleHandler = new EnemyHandler(this, ground, player);

        score = new Score(this);
    }

    // Main game function
    public void run() {
        //Main game thread
        mainThread.run();
    }

    void levelUp() {
        gameLevel++;
    }

    // Show a red "Game Over" text
    private void endTheGame() {
        // Creates a "Game Over" label and Replay button
        gameOver = new GameObject(DataProvider.getGameOverText());
        gameOver.scale(2);
        gameOver.setLocation(DataProvider.getWindowWidth() / 2 - gameOver.getWidth() / 2,
                DataProvider.getWindowHeight() / 2 - gameOver.getHeight() / 2);

        replayBtn = new GameObject(DataProvider.getReplayBtn());
        replayBtn.scale(1.5);
        replayBtn.setLocation(DataProvider.getWindowWidth() / 2 - replayBtn.getWidth() / 2,
                gameOver.getY() + gameOver.getHeight() + replayBtn.getHeight() / 2);

        //Shows them on the screen
        add(gameOver);
        add(replayBtn);

        while (isGameOver) ;
    }

    // Listens to the keyboard for an event
    public void keyPressed(KeyEvent e) {
        if (isGameOver || isPaused) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            case KeyEvent.VK_SPACE:
                player.jump();
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.incline();
                break;

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                break;
            case KeyEvent.VK_R:
                restart();
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (isGameOver || isPaused) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.stopIncline();
                break;
        }
    }


    private void restart() {

        try {
            resetAllItems();
        } catch (Exception e) {
            System.out.println("Null in resetAllItems function");
        }

        // Assigning newly created objects to references again
        Background background = new Background();

        backgroundSlider.removeSlides();
        backgroundSlider = new SeamlessSlider(this, background);

        Ground ground = new Ground();
        groundSlider.removeSlides();
        groundSlider = new SeamlessSlider(this, ground);
        groundSlider.setSliderSpeed(DataProvider.getGroundObstacleSpeed());

        player = new Player(this, ground);
        obstacleHandler.removeObstacles();
        obstacleHandler = new EnemyHandler(this, ground, player);
        score = new Score(this);

        // Call garbage collector explicitly
        System.gc();

        isGameOver = false;
    }

    private void resetAllItems() {
        gameLevel = 0;

        remove(player);
        remove(score);
        remove(gameOver);
        remove(replayBtn);
    }

    public int getGameScore() {
        return score.getScore();
    }



    public class MainLoopThread implements Runnable {
        @Override
        public void run() {
            //Main game loop
            while (!isGameOver) {

                // Playing animations of objects
                backgroundSlider.buildSliderAnim();
                groundSlider.buildSliderAnim();
                player.animate();
                score.animate();

                isGameOver = obstacleHandler.buildObstacleAnim();

                if (isGameOver) {
                    endTheGame();
                }

                if (isPaused) {
                    pauseGame();
                }

                //Some delay - Value of this determines the game speed
                pause(DataProvider.getFrameRate() - gameLevel);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        super.mouseClicked(mouseEvent);

        if (mouseEvent.getID() == MouseEvent.MOUSE_CLICKED && isGameOver) {
            restart();
        } else if (mouseEvent.getID() == MouseEvent.MOUSE_CLICKED && !isGameOver && !isPaused) {
            isPaused = true;
        } else if (mouseEvent.getID() == MouseEvent.MOUSE_CLICKED && !isGameOver && isPaused) {
            resumeGame();
        }
    }

    private volatile boolean isPaused = false;
    private GameObject resumeButton;

    private void pauseGame() {
        resumeButton = new GameObject(DataProvider.getReplayBtn());
        resumeButton.scale(1.5);
        resumeButton.setLocation(DataProvider.getWindowWidth() / 2 - resumeButton.getWidth() / 2,
                getHeight() / 2 - resumeButton.getHeight() / 2);

        //Shows them on the screen
        add(resumeButton);

        while (isPaused) ;
    }

    private void resumeGame() {
        remove(resumeButton);

        isPaused = false;
    }
}
