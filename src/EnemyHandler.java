import acm.graphics.GPoint;

import java.util.ArrayList;
import java.util.Arrays;

public class EnemyHandler {

    private ArrayList<Enemy> enemies;
    private Ground ground;
    private Player player;

    private int collisionIgnoreIndex = 0;

    private int numberOfObstacles;
    private Game game;

    private int[][] playerPixels;
    private int[][] enemyPixels;

    EnemyHandler(Game game, Ground ground, Player player) {
        this.game = game;
        this.ground = ground;
        this.player = player;

        numberOfObstacles = DataProvider.getNumberOfObstacles();
        enemies = new ArrayList<>();

        initObstacles();
    }

    public void removeObstacles() {
        for (Enemy obstacle : enemies) {
            game.remove(obstacle);
        }

        enemies.clear();
    }

    private void initObstacles() {
        for (int i = 0; i < numberOfObstacles; i++) {
            Enemy temp;
            temp = new Cactus();
            locateEnemy(temp, ground);
            enemies.add(temp);
            game.add(temp);
        }
        enemyPixels = enemies.get(0).getPixelArray();
    }

    public boolean buildObstacleAnim() {
        boolean isGameOver = false;

        for (int i = 0; i < enemies.size(); i++) {

            if (enemies.get(i).getWidth() + enemies.get(i).getX() < 0) {
                Enemy temp;
                temp = EnemyFactory.getRandomEnemy(player);

                locateEnemy(temp, ground);
                game.remove(enemies.get(i));
                game.add(temp);
                enemies.set(i, temp);
            } else {
                enemies.get(i).setLocation(enemies.get(i).getX() - DataProvider.getGroundObstacleSpeed(), enemies.get(i).getY());
            }

            enemies.get(i).animate();

            if (intersects(player, enemies.get(i))) {
                player.kill();
                isGameOver = true;
            }
        }

        return isGameOver;
    }

    private void locateEnemy(Enemy enemy, Ground ground) {
        double ow = enemy.getWidth();

        double gw = DataProvider.getWindowWidth();

        double ratio = gw / ow;

        int numOfSpace = (int) Math.floor(ratio);

        int randPlace = (int) Math.round(Math.random() * numOfSpace);

        double maxDistance = DataProvider.getWindowWidth();

        for (Enemy enemy1 : enemies) {
            maxDistance = (enemy1.getX() > maxDistance) ? enemy1.getX() : maxDistance;
        }

        double xPosition = ((DataProvider.getWindowWidth() + randPlace * ow) < (maxDistance + player.getWidth() * 4)) ? (maxDistance + player.getWidth() * 4) : (DataProvider.getWindowWidth() + randPlace * ow);

        if (enemy instanceof Bird) {
            if (Math.round(Math.random() * 100) % 4 == 0) {
                enemy.setLocation(xPosition,
                        ground.getY() - player.getDefaultPlayerHeight() + enemy.getHeight() * 0.8);
            } else {
                enemy.setLocation(xPosition,
                        ground.getY() - player.getDefaultPlayerHeight() - enemy.getHeight() * 0.8);
            }
        } else if (enemy instanceof Cactus) {
            enemy.setLocation(xPosition,
                    ground.getY() - enemy.getHeight() + 5);
        }
    }


    private boolean intersects(GameObject gameObject1, GameObject gameObject2) {

        if (gameObject2.getX() >= gameObject1.getX() + gameObject1.getWidth()) {
            return false;
        } else if (gameObject2.getY() >= gameObject1.getY() + gameObject1.getHeight()) {
            return false;
        } else if (gameObject1.getX() >= gameObject2.getX() + gameObject2.getWidth()) {
            return false;
        } else if (gameObject1.getY() >= gameObject2.getY() + gameObject2.getHeight()) {
            return false;
        } else {
            playerPixels = gameObject1.getPixelArray();
            enemyPixels = gameObject2.getPixelArray();

            int[] arrX = {
                    (int) gameObject1.getX(),
                    (int) gameObject2.getX(),
                    (int) gameObject1.getX() + (int) gameObject1.getWidth(),
                    (int) gameObject2.getX() + (int) gameObject2.getWidth()
            };

            int[] arrY = {
                    (int) gameObject1.getY(),
                    (int) gameObject2.getY(),
                    (int) gameObject1.getY() + (int) gameObject1.getHeight(),
                    (int) gameObject2.getY() + (int) gameObject2.getHeight()
            };

            Arrays.sort(arrX);
            Arrays.sort(arrY);

            GPoint ff = new GPoint(arrX[1], arrY[1]);  // first first
            GPoint ll = new GPoint(arrX[2], arrY[2]); // last last

            if (checkForPixelCollision(ff, ll, gameObject1, gameObject2)) {
                collisionIgnoreIndex++;
            } else {
                collisionIgnoreIndex = 0;
            }
            return collisionIgnoreIndex == DataProvider.getCollisionIgnoreValue();
        }
    }

    private boolean checkForPixelCollision(GPoint ff, GPoint ll, GameObject gameObject1, GameObject gameObject2) {
        for (int j = (int) ff.getY(); j < (int) ll.getY(); j++) {
            for (int i = (int) ff.getX(); i < (int) ll.getX(); i++) {
                if (isColorfulPixel(playerPixels, i, j, (int) gameObject1.getX(), (int) gameObject1.getY(), DataProvider.getPlayerScale()) &&
                        isColorfulPixel(enemyPixels, i, j, (int) gameObject2.getX(), (int) gameObject2.getY(), DataProvider.getObstacleScale())
                        ) {
                    return true; // collision detected
                }
            }
        }
        return false; // no collision detected
    }

    private boolean isColorfulPixel(int[][] pixelArray, int i, int j, int X, int Y, double scale) {

        try {
            return pixelArray[(int) ((j - Y) / scale)][(int) ((i - X) / scale)] != 0 &&
                    pixelArray[(int) ((j - Y) / scale)][(int) ((i - X) / scale)] != 16777215;
        } catch (Exception e) {
            System.out.println("Array out of size index problem!\n l.Y = " + String.valueOf((int) ((j - Y) / scale))
                    + "  l.X = " + String.valueOf((int) ((i - X) / scale)) + "\n");
            return true;
        }
    }

}
