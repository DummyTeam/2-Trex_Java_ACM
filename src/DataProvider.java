
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class DataProvider {

    private static ArrayList<String> settingsFileData;

    private static String SETTINGS_FILE_PATH = "D:\\Repositories\\ACM_Java_Main\\src\\Settings.data";
    private static String SCORES_FILE_PATH = "D:\\Repositories\\ACM_Java_Main\\src\\Scores.txt";

    private static int WINDOW_WIDTH = 1000;
    private static int WINDOW_HEIGHT = 500;

    private static int FRAME_RATE = 9;
    private static int NUMBER_OF_OBSTACLES = 2;
    private static int GROUND_OBSTACLE_SPEED = 4;

    private static String PLAYER_STATIC_MEDIA_PATH = "D:\\Repositories\\ACM_Java_Main\\res\\static.png";
    private static String PLAYER_DEAD_MEDIA_PATH = "D:\\Repositories\\ACM_Java_Main\\res\\dead.png";

    private static String GAME_OVER_TEXT = "D:\\Repositories\\ACM_Java_Main\\res\\game_over_text.png";
    private static String REPLAY_BTN = "D:\\Repositories\\ACM_Java_Main\\res\\replay.png";

    private static String DEATH_SOUND = "D:\\Repositories\\ACM_Java_Main\\res\\sound\\hit.wav";
    private static String JUMP_SOUND = "D:\\Repositories\\ACM_Java_Main\\res\\sound\\jump.wav";

    private static String BACKGROUND = "D:\\Repositories\\ACM_Java_Main\\res\\background.jpg";
    private static String TRIPLE_CACTUS = "D:\\Repositories\\ACM_Java_Main\\res\\triple_multi_cactus.png";
    private static String SINGLE_CACTUS = "D:\\Repositories\\ACM_Java_Main\\res\\single_big_cactus.png";
    private static String DOUBLE_CACTUS = "D:\\Repositories\\ACM_Java_Main\\res\\double_big_cactus.png";
    private static String GROUND_IMG = "D:\\Repositories\\ACM_Java_Main\\res\\ground.png";

    private static double PLAYER_SCALE = 0.5;
    private static double OBSTACLE_SCALE = 0.5;
    private static double BACKGROUND_SCALE = 0.5;
    private static double BIRD_SCALE = 1.5;

    private static int COLLISION_IGNORE_VALUE = 1;

    private static int PLAYER_INITIAL_X_POS = 100;
    private static double PLAYER_TIME_RES = 0.3;
    private static double INITIAL_VELOCITY = 25;
    private static double ACCELERATION = 1.5;
    private static int RUNNING_ANIM_SPEED = 14;

    private static String BIRD_UP = "D:\\Repositories\\ACM_Java_Main\\res\\bird_up.png";
    private static String BIRD_DOWN = "D:\\Repositories\\ACM_Java_Main\\res\\bird_down.png";

    private static String PLAYER_SPRITE_1 = "D:\\Repositories\\ACM_Java_Main\\res\\run1.png";
    private static String PLAYER_SPRITE_2 = "D:\\Repositories\\ACM_Java_Main\\res\\run2.png";

    private static String PLAYER_INCLINE_1 = "D:\\Repositories\\ACM_Java_Main\\res\\incline1.png";
    private static String PLAYER_INCLINE_2 = "D:\\Repositories\\ACM_Java_Main\\res\\incline2.png";

    public static ArrayList<String> getPlayerSpriteList() {
        ArrayList<String> sprites = new ArrayList<>();

        sprites.add(PLAYER_SPRITE_1);
        sprites.add(PLAYER_SPRITE_2);

        return sprites;
    }

    public static ArrayList<String> getPlayerInclineList() {
        ArrayList<String> sprites = new ArrayList<>();

        sprites.add(PLAYER_INCLINE_1);
        sprites.add(PLAYER_INCLINE_2);

        return sprites;
    }

    public static String getBirdUp() {
        return BIRD_UP;
    }

    public static ArrayList<String> getBirdSpriteList() {
        ArrayList<String> sprites = new ArrayList<>();

        sprites.add(BIRD_UP);
        sprites.add(BIRD_DOWN);

        return sprites;
    }

    public static double getBackgroundScale() {
        return BACKGROUND_SCALE;
    }

    public static String getGameOverText() {
        return GAME_OVER_TEXT;
    }

    public static String getReplayBtn() {
        return REPLAY_BTN;
    }

    public static int getCollisionIgnoreValue() {
        return COLLISION_IGNORE_VALUE;
    }

    public static int getPlayerInitialXPos() {
        return PLAYER_INITIAL_X_POS;
    }

    public static double getPlayerScale() {
        return PLAYER_SCALE;
    }

    public static double getBirdScale() {
        return BIRD_SCALE;
    }

    public static int getRunningAnimSpeed() {
        return RUNNING_ANIM_SPEED;
    }

    public static double getObstacleScale() {
        return OBSTACLE_SCALE;
    }

    public static double getPlayerTimeRes() {
        return PLAYER_TIME_RES;
    }

    public static double getInitialVelocity() {
        return INITIAL_VELOCITY;
    }

    public static double getAcceleration() {
        return ACCELERATION;
    }

    public static int getGroundObstacleSpeed() {
        return GROUND_OBSTACLE_SPEED;
    }

    public static int getNumberOfObstacles() {
        return NUMBER_OF_OBSTACLES;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static Dimension getWindowSize() {
        return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static int getFrameRate() {
        return FRAME_RATE;
    }

    public static String getStaticPlayerImg() {
        return PLAYER_STATIC_MEDIA_PATH;
    }

    public static String getDeadPlayerImg() {
        return PLAYER_DEAD_MEDIA_PATH;
    }

    public static String getGroundImg() {
        return GROUND_IMG;
    }

    public static String getBckImg() {
        return BACKGROUND;
    }

    public static String getTripleCactusImg() {
        return TRIPLE_CACTUS;
    }

    public static String getDoubleCactusImg() {
        return DOUBLE_CACTUS;
    }

    public static String getSingleCactusImg() {
        return SINGLE_CACTUS;
    }

    public static void readFromFile() {
        settingsFileData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(SETTINGS_FILE_PATH))) {
            for (String line; (line = br.readLine()) != null; ) {
                line = line.trim();
                if (!line.isEmpty() && line.charAt(0) != '#') {
                    settingsFileData.add(line);
                }
                //System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("An error occurred during reading from file");
        }

        for (String str : settingsFileData) {
            int i = str.indexOf(' ');
            String command = str.substring(0, i);
            String data = str.substring(i);

            switch (command) {
                case "ps":
                    PLAYER_SCALE = Double.valueOf(data.trim());
                    break;
                case "os":
                    OBSTACLE_SCALE = Double.valueOf(data.trim());
                    break;
                case "fr":
                    FRAME_RATE = Integer.valueOf(data.trim());
                    break;
                case "ww":
                    WINDOW_WIDTH = Integer.valueOf(data.trim());
                    break;
                case "wh":
                    WINDOW_HEIGHT = Integer.valueOf(data.trim());
                    break;
                case "ptr":
                    PLAYER_TIME_RES = Double.valueOf(data.trim());
                    break;
                case "iv":
                    INITIAL_VELOCITY = Double.valueOf(data.trim());
                    break;
                case "acc":
                    ACCELERATION = Double.valueOf(data.trim());
                    break;
                default:
                    break;
            }
        }
    }

    public static void writeScore(int score) {
        try {
            String data = new String(Files.readAllBytes(Paths.get(SCORES_FILE_PATH)));

            FileWriter fileWriter = new FileWriter(SCORES_FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(score));

            bufferedWriter.close();

        } catch (Exception e) {
            System.out.println("Error happened during writing to Scores file");
        }
    }

    public static void resetScoreFile() {
        try {
            FileWriter fileWriter = new FileWriter(SCORES_FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error happened during writing to resetScoreFile");
        }
    }

    public static int getHighestScore() {
        ArrayList<Integer> scores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(SCORES_FILE_PATH))) {
            for (String line; (line = br.readLine()) != null; ) {
                line = line.trim();
                if (!line.isEmpty()) {
                    scores.add(Integer.parseInt(line));
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred during reading from file in high score");
        }

        Collections.sort(scores);

        return (scores.size() > 0) ? scores.get(scores.size() - 1) : 0;
    }


    public static String getDeathSound() {
        return DEATH_SOUND;
    }

    public static String getJumpSound() {
        return JUMP_SOUND;
    }
}
