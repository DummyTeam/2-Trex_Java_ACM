class Cactus extends Enemy {
    Cactus() {
        super(DataProvider.getSingleCactusImg());
        scale(DataProvider.getObstacleScale());
    }

    @Override
    public void animate() {

    }
}
