class Background extends GameObject {
    Background() {
        super(DataProvider.getBckImg());
        setLocation(0, 0);
        scale(DataProvider.getBackgroundScale());
    }
}
