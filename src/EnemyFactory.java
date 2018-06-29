public class EnemyFactory {

    public static Enemy getRandomEnemy(Player player) {
        Enemy enemy;

        int value = (int) Math.round(Math.random() * 100);

        if (value < 30) {
            enemy = new Cactus();
        } else if (value < 60) {
            enemy = new DoubleCactus();
        } else if (value < 90) {
            enemy = new ThreeCactus();
        } else {
            enemy = new Bird(player);
        }

        return enemy;
    }

}
